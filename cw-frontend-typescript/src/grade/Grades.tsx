import React from "react";
import axios from "axios";
import {
    Breadcrumbs,
    Link,
    Typography,
    Alert,
    Grid,
    Button,
    Card,
    CardContent,
    Box
} from "@mui/material";
import {ArrowDownward, ArrowUpward, Delete} from "@mui/icons-material";
import App from "../App.tsx";
import {
    EntityModelGrade,
    EntityModelStudent,
    EntityModelModule,
} from "../api/index";
import { API_ENDPOINT } from "../config";
import AddGrade from "./AddGrade";
import NavigationBar from "../NavigationBar";
import AddStudent from "../student/AddStudent.tsx";

function Grades() {
    const [grades, setGrades] = React.useState<EntityModelGrade[]>([]);
    const [error, setError] = React.useState<string>();
    const [loading, setLoading] = React.useState<boolean>(true);
    const [sortConfig, setSortConfig] = React.useState<{ key: string; direction: 'asc' | 'desc' } | null>(null);

    React.useEffect(() => {
        updateGrades();
    }, []);

    async function updateGrades() {
        setLoading(true);
        try {
            const response = await axios.get(`${API_ENDPOINT}/grades`);
            const gradeData = response.data._embedded.grades;

            // Fetch student and module data for each grade asynchronously
            const gradesWithDetails = await Promise.all(
                gradeData.map(async (grade: EntityModelGrade) => {
                    const studentResponse = grade._links?.student ? await axios.get(grade._links.student.href) : null;
                    const moduleResponse = grade._links?.module ? await axios.get(grade._links.module.href) : null;
                    return {
                        ...grade,
                        student: studentResponse ? studentResponse.data : null,
                        module: moduleResponse ? moduleResponse.data : null
                    };
                })
            );

            setGrades(gradesWithDetails);
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    }

    function exportToPDF() {
        const printContents = document.getElementById("print-section")?.innerHTML;

        if (printContents) {
            const iframe = document.createElement("iframe");
            iframe.style.position = "absolute";
            iframe.style.top = "-9999px";
            document.body.appendChild(iframe);

            const iframeDoc = iframe.contentWindow?.document;
            iframeDoc?.open();
            iframeDoc?.write(`
        <html>
            <head>
                <title>Grade List</title>
                <style>
                    body { font-family: Arial, sans-serif; }
                    table { width: 100%; border-collapse: collapse; }
                    th, td { text-align: left; padding: 8px; border: 1px solid #ddd; }
                    th { background-color: #f2f2f2; }
                    tr:nth-child(even) { background-color: #f9f9f9; }
                    h2 { text-align: center; }
                </style>
            </head>
            <body>
                <h2>Grade List</h2>
                <table>
                    <thead>
                        <tr><th>Student</th><th>Module</th><th>Score</th></tr>
                    </thead>
                    <tbody>
                        ${grades
                .map((g) => `
                            <tr>
                                <td>${g.student ? `${g.student.firstName} ${g.student.lastName} (${g.student.id})` : 'Loading...'}</td>
                                <td>${g.module ? `${g.module.code} - ${g.module.name}` : 'Loading...'}</td>
                                <td>${g.score}</td>
                            </tr>
                        `)
                .join("")}
                    </tbody>
                </table>
            </body>
        </html>
        `);
            iframeDoc?.close();
            iframe.contentWindow?.focus();
            iframe.contentWindow?.print();
            iframe.onload = () => {
                document.body.removeChild(iframe);
            };
        }
    }

    function exportToCSV() {
        const csvHeader = "Student,Module,Score\n"; // Define the header row
        const csvRows = grades.map((g) => {
            const studentName = g.student ? `${g.student.firstName} ${g.student.lastName} (${g.student.id})` : "N/A";
            const moduleName = g.module ? `${g.module.code} - ${g.module.name}` : "N/A";
            const score = g.score || "N/A";
            return `${studentName},${moduleName},${score}`;
        });

        // Combine header and rows into a single CSV string
        const csvContent = csvHeader + csvRows.join("\n");

        // Create a blob with the CSV content and download it
        const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
        const url = URL.createObjectURL(blob);

        // Create a temporary link element and trigger the download
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", "grades.csv");
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    function sortGrades(grades: EntityModelGrade[]) {
        if (!sortConfig) return grades;

        const sortedGrades = [...grades].sort((a, b) => {
            let valueA, valueB;

            switch (sortConfig.key) {
                case 'student':
                    valueA = a.student ? `${a.student.firstName} ${a.student.lastName}` : '';
                    valueB = b.student ? `${b.student.firstName} ${b.student.lastName}` : '';
                    break;
                case 'module':
                    valueA = a.module ? `${a.module.name}` : '';
                    valueB = b.module ? `${b.module.name}` : '';
                    break;
                case 'score':
                    valueA = a.score || 0;
                    valueB = b.score || 0;
                    break;
                default:
                    return 0;
            }

            if (valueA < valueB) return sortConfig.direction === 'asc' ? -1 : 1;
            if (valueA > valueB) return sortConfig.direction === 'asc' ? 1 : -1;
            return 0;
        });

        return sortedGrades;
    }

    function handleSort(key: string) {
        setSortConfig((prevSortConfig) => {
            if (prevSortConfig && prevSortConfig.key === key) {
                return {key, direction: prevSortConfig.direction === 'asc' ? 'desc' : 'asc'};
            }
            return {key, direction: 'asc'};
        });
    }

    const sortedGrades = sortGrades(grades);

    function getSortIndicator(key: string) {
        if (!sortConfig || sortConfig.key !== key) return null;

        return sortConfig.direction === 'asc' ? (
            <ArrowUpward sx={{ fontSize: "1rem", color: "blue", marginLeft: "5px" }} />
        ) : (
            <ArrowDownward sx={{ fontSize: "1rem", color: "blue", marginLeft: "5px" }} />
        );
    }

    return (
        <App>
            <Box sx={{ marginBottom: "20px" }}>
                <AddGrade update={updateGrades}/>
            </Box>
            {error && <Alert severity="error">{error}</Alert>}
            {loading && !error && <Alert severity="info">Loading grades...</Alert>}
            {!loading && grades.length < 1 && <Alert severity="warning">No grades available</Alert>}
            {grades.length > 0 && (
                <>
                    <Box sx={{padding: "10px", border: "1px solid #ccc", borderRadius: "5px", marginBottom: "20px"}}>
                        <Grid container spacing={2}>
                            <Grid item xs={3}>
                                <Typography
                                    variant="body2"
                                    sx={{fontWeight: sortConfig?.key === 'student' ? 'bold' : 'normal', cursor: "pointer"}}
                                    onClick={() => handleSort('student')}
                                >
                                    Student (ID) {getSortIndicator('student')}
                                </Typography>
                            </Grid>
                            <Grid item xs={3}>
                                <Typography
                                    variant="body2"
                                    sx={{fontWeight: sortConfig?.key === 'module' ? 'bold' : 'normal', cursor: "pointer"}}
                                    onClick={() => handleSort('module')}
                                >
                                    Module {getSortIndicator('module')}
                                </Typography>
                            </Grid>
                            <Grid item xs={3}>
                                <Typography
                                    variant="body2"
                                    sx={{fontWeight: sortConfig?.key === 'score' ? 'bold' : 'normal', cursor: "pointer"}}
                                    onClick={() => handleSort('score')}
                                >
                                    Score {getSortIndicator('score')}
                                </Typography>
                            </Grid>
                            <Grid item xs={3}>
                                <Typography variant="body2">
                                    Actions
                                </Typography>
                            </Grid>
                        </Grid>
                    </Box>
                    <div id="print-section">
                        {sortedGrades.map((g) => (
                            <GradeRow key={g.id} grade={g} onDelete={updateGrades}/>
                        ))}
                    </div>
                    <Box sx={{display: "flex", justifyContent: "center", margin: "20px 0 20px 0"}}>
                        <Button variant="contained" color="secondary" onClick={exportToPDF}>Export as PDF</Button>
                        <Button variant="contained" color="primary" onClick={exportToCSV} sx={{ marginLeft: "10px" }}>
                            Export as CSV
                        </Button>
                    </Box>
                </>
            )}
        </App>
    );
}

function GradeRow(props: { grade: EntityModelGrade; onDelete: Function, key?: number | undefined }) {
    const { grade, onDelete } = props;
    const [student, setStudent] = React.useState<EntityModelStudent>();
    const [module, setModule] = React.useState<EntityModelModule>();

    React.useEffect(() => {
        axios
            .get(grade._links!.module!.href!)
            .then((response) => setModule(response.data));
        axios
            .get(grade._links!.student!.href!)
            .then((response) => setStudent(response.data));
    }, [grade]);

    function deleteGrade() {
        axios
            .delete(grade._links!.self!.href!)
            .then(() => onDelete())
            .catch((error) => {
                console.error("Error deleting grade:", error);
            });
    }

    return (
        <Card
            variant="outlined"
            sx={{
                margin: "10px 0",
                transition: "background-color 0.3s ease",
                ":hover": {
                    backgroundColor: "#aaaaaa",
                },
            }}
        >
            <CardContent>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={3}>
                        {student && (
                            <Typography variant="body2">
                                {student.firstName} {student.lastName} ({student.id})
                            </Typography>
                        )}
                    </Grid>
                    <Grid item xs={3}>
                        {module && (
                            <Typography variant="body2">
                                {module.code} - {module.name}
                            </Typography>
                        )}
                    </Grid>
                    <Grid item xs={3}>
                        <Typography variant="body2">{grade.score}</Typography>
                    </Grid>
                    <Grid item xs={3}>
                        <Button
                            color="error"
                            variant="contained"
                            size="small"
                            onClick={deleteGrade}
                            startIcon={<Delete />}
                        >
                            Delete
                        </Button>
                    </Grid>
                </Grid>
            </CardContent>
        </Card>
    );
}

export default Grades;

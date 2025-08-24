import React from "react";
import {
    Breadcrumbs,
    Link,
    Typography,
    Alert,
    Grid,
    Button,
    Box,
    CardContent,
    Card,
} from "@mui/material";
import { Delete, ArrowUpward, ArrowDownward } from "@mui/icons-material";
import App from "../App.tsx";
import axios from "axios";
import { EntityModelStudent } from "../api/entityModelStudent.ts";
import { API_ENDPOINT } from "../config";
import AddStudent from "./AddStudent";
import NavigationBar from "../NavigationBar";

function Students() {
    const [students, setStudents] = React.useState<EntityModelStudent[]>([]);
    const [error, setError] = React.useState<string>();
    const [sortConfig, setSortConfig] = React.useState<{ key: string; direction: 'asc' | 'desc' } | null>(null);

    React.useEffect(() => {
        updateStudents();
    }, []);

    function updateStudents() {
        axios
            .get(`${API_ENDPOINT}/students`)
            .then((response) => {
                setStudents(response.data._embedded.students);
            })
            .catch((error) => {
                setError(error.message);
            });
    }

    function deleteStudent(id: number | undefined) {
        axios
            .delete(`${API_ENDPOINT}/students/${id}`)
            .then(() => {
                updateStudents();
            })
            .catch((error) => {
                setError(error.message);
            });
    }

    function sortStudents(students: EntityModelStudent[]) {
        if (!sortConfig) return students;

        const sortedStudents = [...students].sort((a, b) => {
            let valueA, valueB;

            switch (sortConfig.key) {
                case 'id':
                    valueA = a.id;
                    valueB = b.id;
                    break;
                case 'firstName':
                    valueA = a.firstName;
                    valueB = b.firstName;
                    break;
                case 'lastName':
                    valueA = a.lastName;
                    valueB = b.lastName;
                    break;
                case 'username':
                    valueA = a.username;
                    valueB = b.username;
                    break;
                case 'email':
                    valueA = a.email;
                    valueB = b.email;
                    break;
                default:
                    return 0;
            }

            if (valueA < valueB) return sortConfig.direction === 'asc' ? -1 : 1;
            if (valueA > valueB) return sortConfig.direction === 'asc' ? 1 : -1;
            return 0;
        });

        return sortedStudents;
    }

    function handleSort(key: string) {
        setSortConfig((prevSortConfig) => {
            if (prevSortConfig && prevSortConfig.key === key) {
                return { key, direction: prevSortConfig.direction === 'asc' ? 'desc' : 'asc' };
            }
            return { key, direction: 'asc' };
        });
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
                    <title>Student List</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            margin: 0;
                            padding: 0;
                        }
                        table {
                            width: 100%;
                            border-collapse: collapse;
                            margin: 20px 0;
                        }
                        th, td {
                            text-align: left;
                            padding: 8px;
                            border: 1px solid #ddd;
                        }
                        th {
                            background-color: #f2f2f2;
                        }
                        tr:nth-child(even) {
                            background-color: #f9f9f9;
                        }
                    </style>
                </head>
                <body>
                    <h2>Student List</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Student ID</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Username</th>
                                <th>Email</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${students.map((s) => `
                                <tr>
                                    <td>${s.id}</td>
                                    <td>${s.firstName}</td>
                                    <td>${s.lastName}</td>
                                    <td>${s.username}</td>
                                    <td>${s.email}</td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                </body>
            </html>
        `);
            iframeDoc?.close();

            iframe.contentWindow?.focus();
            iframe.contentWindow?.print();
            document.body.removeChild(iframe);
        }
    }

    function exportToCSV() {
        // Create CSV content
        const csvHeader = "Student ID,First Name,Last Name,Username,Email\n";
        const csvRows = students.map(
            (s) =>
                `${s.id},"${s.firstName}","${s.lastName}","${s.username}","${s.email}"`
        ).join("\n");
        const csvContent = csvHeader + csvRows;

        // Create a blob and download the file
        const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
        const url = URL.createObjectURL(blob);

        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", "students.csv");
        link.style.display = "none";

        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    const sortedStudents = sortStudents(students);

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
                <AddStudent update={updateStudents} />
            </Box>
            {error && <Alert severity="error">{error}</Alert>}
            {!error && students.length < 1 && (
                <Alert severity="warning">No students available</Alert>
            )}
            {students.length > 0 && (
                <div>
                    <div>
                        <Box
                            sx={{
                                display: "flex",
                                justifyContent: "space-between",
                                alignItems: "center",
                                padding: "10px",
                                border: "1px solid #ccc",
                                borderRadius: "5px",
                                marginBottom: "20px",
                            }}
                        >
                            <Grid container spacing={2}>
                                <Grid item xs={2}>
                                    <Typography
                                        variant="body2"
                                        sx={{ fontWeight: sortConfig?.key === 'id' ? 'bold' : 'normal', cursor: "pointer" }}
                                        onClick={() => handleSort('id')}
                                    >
                                        Student ID {getSortIndicator('id')}
                                    </Typography>
                                </Grid>
                                <Grid item xs={2}>
                                    <Typography
                                        variant="body2"
                                        sx={{ fontWeight: sortConfig?.key === 'firstName' ? 'bold' : 'normal', cursor: "pointer" }}
                                        onClick={() => handleSort('firstName')}
                                    >
                                        First Name {getSortIndicator('firstName')}
                                    </Typography>
                                </Grid>
                                <Grid item xs={2}>
                                    <Typography
                                        variant="body2"
                                        sx={{ fontWeight: sortConfig?.key === 'lastName' ? 'bold' : 'normal', cursor: "pointer" }}
                                        onClick={() => handleSort('lastName')}
                                    >
                                        Last Name {getSortIndicator('lastName')}
                                    </Typography>
                                </Grid>
                                <Grid item xs={2}>
                                    <Typography
                                        variant="body2"
                                        sx={{ fontWeight: sortConfig?.key === 'username' ? 'bold' : 'normal', cursor: "pointer" }}
                                        onClick={() => handleSort('username')}
                                    >
                                        Username {getSortIndicator('username')}
                                    </Typography>
                                </Grid>
                                <Grid item xs={2}>
                                    <Typography
                                        variant="body2"
                                        sx={{ fontWeight: sortConfig?.key === 'email' ? 'bold' : 'normal', cursor: "pointer" }}
                                        onClick={() => handleSort('email')}
                                    >
                                        Email {getSortIndicator('email')}
                                    </Typography>
                                </Grid>
                                <Grid item xs={2}>
                                    <Typography variant="body2">
                                        Actions
                                    </Typography>
                                </Grid>
                            </Grid>
                        </Box>
                        <div id="print-section">
                            {sortedStudents.map((s) => (
                                <Card
                                    key={s.id}
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
                                        <Grid container spacing={2}>
                                            <Grid item xs={2}>
                                                <Typography variant="body2">{s.id}</Typography>
                                            </Grid>
                                            <Grid item xs={2}>
                                                <Typography variant="body2">{s.firstName}</Typography>
                                            </Grid>
                                            <Grid item xs={2}>
                                                <Typography variant="body2">{s.lastName}</Typography>
                                            </Grid>
                                            <Grid item xs={2}>
                                                <Typography variant="body2">{s.username}</Typography>
                                            </Grid>
                                            <Grid item xs={2}>
                                                <Typography variant="body2">{s.email}</Typography>
                                            </Grid>
                                            <Grid item xs={2}>
                                                <Button
                                                    variant="contained"
                                                    size="small"
                                                    color="error"
                                                    onClick={() => deleteStudent(s.id)}
                                                    startIcon={<Delete />}
                                                >
                                                    Delete
                                                </Button>
                                            </Grid>
                                        </Grid>
                                    </CardContent>
                                </Card>
                            ))}
                        </div>
                    </div>
                    <Box sx={{ display: "flex", justifyContent: "center", margin: "20px 0 20px 0" }}>
                        <Button variant="contained" color="secondary" onClick={exportToPDF}>
                            Export as PDF
                        </Button>
                        <Button variant="contained" color="primary" onClick={exportToCSV} sx={{ marginLeft: "10px" }}>
                            Export as CSV
                        </Button>
                    </Box>
                </div>
            )}
        </App>
    );
}

export default Students;

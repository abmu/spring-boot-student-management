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
    Box,
} from "@mui/material";
import {ArrowDownward, ArrowUpward, Delete} from "@mui/icons-material";
import App from "../App.tsx";
import { EntityModelModule } from "../api/entityModelModule.ts";
import { API_ENDPOINT } from "../config";
import AddModule from "./AddModule";
import NavigationBar from "../NavigationBar";
import AddGrade from "../grade/AddGrade.tsx";

function ModuleRow(props: { module: EntityModelModule; onDelete: Function }) {
    const { module, onDelete } = props;

    function deleteModule() {
        axios
            .delete(`${API_ENDPOINT}/modules/${module.code}`)
            .then(() => onDelete())
            .catch((error) => {
                console.error("Error deleting module:", error);
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
                        <Typography variant="body2">{module.code}</Typography>
                    </Grid>
                    <Grid item xs={3}>
                        <Typography variant="body2">{module.name}</Typography>
                    </Grid>
                    <Grid item xs={3}>
                        <Typography variant="body2">{module.mnc ? "Yes" : "No"}</Typography>
                    </Grid>
                    <Grid item xs={3}>
                        <Button
                            color="error"
                            variant="contained"
                            size="small"
                            onClick={deleteModule}
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

function Modules() {
    const [modules, setModules] = React.useState<EntityModelModule[]>([]);
    const [error, setError] = React.useState<string>();
    const [sortConfig, setSortConfig] = React.useState<{ key: string; direction: 'asc' | 'desc' } | null>(null);

    React.useEffect(() => {
        updateModules();
    }, []);

    function updateModules() {
        axios
            .get(`${API_ENDPOINT}/modules`)
            .then((response) => {
                setModules(response.data._embedded.modules);
            })
            .catch((response) => {
                setError(response.message);
            });
    }

    function sortModules(modules: EntityModelModule[]) {
        if (!sortConfig) return modules;

        const sortedModules = [...modules].sort((a, b) => {
            let valueA, valueB;

            switch (sortConfig.key) {
                case 'code':
                    valueA = a.code;
                    valueB = b.code;
                    break;
                case 'name':
                    valueA = a.name;
                    valueB = b.name;
                    break;
                case 'mnc':
                    valueA = a.mnc ? 1 : 0;
                    valueB = b.mnc ? 1 : 0;
                    break;
                default:
                    return 0;
            }

            if (valueA < valueB) return sortConfig.direction === 'asc' ? -1 : 1;
            if (valueA > valueB) return sortConfig.direction === 'asc' ? 1 : -1;
            return 0;
        });

        return sortedModules;
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
                    <title>Module List</title>
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
                    <h2>Module List</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Module Code</th>
                                <th>Module Name</th>
                                <th>MNC?</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${modules.map((m) => `
                                <tr>
                                    <td>${m.code}</td>
                                    <td>${m.name}</td>
                                    <td>${m.mnc ? "Yes" : "No"}</td>
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
        const csvContent =
            "data:text/csv;charset=utf-8," +
            ["Module Code,Module Name,MNC?"]
                .concat(
                    modules.map((m) => `${m.code},${m.name},${m.mnc ? "Yes" : "No"}`)
                )
                .join("\n");

        const encodedUri = encodeURI(csvContent);
        const link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "modules.csv");
        document.body.appendChild(link); // Required for Firefox
        link.click();
        document.body.removeChild(link);
    }

    const sortedModules = sortModules(modules);

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
                <AddModule update={updateModules} />
            </Box>
            {error && <Alert severity="error">{error}</Alert>}
            {!error && modules.length < 1 && (
                <Alert severity="warning">No modules available</Alert>
            )}
            {modules.length > 0 && (
                <>
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
                            <Grid item xs={3}>
                                <Typography
                                    variant="body2"
                                    sx={{ fontWeight: sortConfig?.key === 'code' ? 'bold' : 'normal', cursor: "pointer" }}
                                    onClick={() => handleSort('code')}
                                >
                                    Module Code {getSortIndicator('code')}
                                </Typography>
                            </Grid>
                            <Grid item xs={3}>
                                <Typography
                                    variant="body2"
                                    sx={{ fontWeight: sortConfig?.key === 'name' ? 'bold' : 'normal', cursor: "pointer" }}
                                    onClick={() => handleSort('name')}
                                >
                                    Module Name {getSortIndicator('name')}
                                </Typography>
                            </Grid>
                            <Grid item xs={3}>
                                <Typography
                                    variant="body2"
                                    sx={{ fontWeight: sortConfig?.key === 'mnc' ? 'bold' : 'normal', cursor: "pointer" }}
                                    onClick={() => handleSort('mnc')}
                                >
                                    MNC? {getSortIndicator('mnc')}
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
                        {sortedModules.map((m) => (
                            <ModuleRow key={m.code} module={m} onDelete={updateModules} />
                        ))}
                    </div>
                    <Box sx={{ display: "flex", justifyContent: "center", margin: "20px 0 20px 0" }}>
                        <Button variant="contained" color="secondary" onClick={exportToPDF}>
                            Export as PDF
                        </Button>
                        <Button variant="contained" color="primary" onClick={exportToCSV} sx={{ marginLeft: "10px" }}>
                            Export as CSV
                        </Button>
                    </Box>
                </>
            )}
        </App>
    );
}

export default Modules;

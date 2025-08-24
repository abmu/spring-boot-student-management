import React from "react";
import axios from "axios";
import {
    Paper,
    Button,
    Typography,
    Select,
    MenuItem,
    TextField,
    Alert,
    Grid,
    FormControl,
    InputLabel,
} from "@mui/material";
import { Add } from "@mui/icons-material";
import {
    AddGradeBody,
    EntityModelStudent,
    EntityModelModule,
} from "../api/index";
import { API_ENDPOINT } from "../config";

function AddGrade(props: { update: Function }) {
    const [grade, setGrade] = React.useState<AddGradeBody>({});
    const [students, setStudents] = React.useState<EntityModelStudent[]>([]);
    const [modules, setModules] = React.useState<EntityModelModule[]>([]);
    const [error, setError] = React.useState<string>();
    const [scoreError, setScoreError] = React.useState<string>();
    const [loading, setLoading] = React.useState(false);

    React.useEffect(() => {
        axios
            .get(`${API_ENDPOINT}/students`)
            .then((response) => {
                setStudents(response.data._embedded.students);
            })
            .catch((error) => setError(error.message));

        axios
            .get(`${API_ENDPOINT}/modules`)
            .then((response) => {
                setModules(response.data._embedded.modules);
            })
            .catch((error) => setError(error.message));
    }, []);

    function validateForm() {
        if (!grade.student_id || !grade.module_code || grade.score === undefined) {
            setError("Please fill in all fields.");
            return false;
        }
        if (isNaN(Number(grade.score))) {
            setScoreError("Score must be a valid number.");
            return false;
        }
        if (!Number.isInteger(Number(grade.score))) {
            setScoreError("Score must be an integer.");
            return false;
        }
        if (grade.score < 0 || grade.score > 100) {
            setScoreError("Score must be between 0 and 100.");
            return false;
        }
        setError(undefined);
        setScoreError(undefined);
        return true;
    }

    function handleSubmit() {
        if (!validateForm()) return;
        setLoading(true);
        axios
            .post(`${API_ENDPOINT}/grades/addGrade`, grade)
            .then(() => {
                props.update();
                setGrade({}); // Reset form
                setLoading(false);
            })
            .catch((error) => {
                setError(error.message);
                setLoading(false);
            });
    }

    return (
        <Paper sx={{ padding: "30px", marginTop: "20px" }} elevation={3}>
            <Typography variant="h5" gutterBottom>
                Add Grade
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} md={6}>
                    <FormControl fullWidth>
                        <InputLabel id="student-select-label">Student</InputLabel>
                        <Select
                            labelId="student-select-label"
                            value={grade.student_id ?? ""}
                            onChange={(e) => setGrade({ ...grade, student_id: e.target.value })}
                            label="Student"
                        >
                            {students.map((s) => (
                                <MenuItem key={s.id} value={s.id}>
                                    {`${s.firstName} ${s.lastName} (${s.id})`}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={12} md={6}>
                    <FormControl fullWidth>
                        <InputLabel id="module-select-label">Module</InputLabel>
                        <Select
                            labelId="module-select-label"
                            value={grade.module_code ?? ""}
                            onChange={(e) =>
                                setGrade({ ...grade, module_code: e.target.value })
                            }
                            label="Module"
                        >
                            {modules.map((m) => (
                                <MenuItem key={m.code} value={m.code}>
                                    {`${m.code} - ${m.name}`}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={12} md={6}>
                    <TextField
                        fullWidth
                        label="Score"
                        type="number"
                        value={grade.score ?? ""}
                        onChange={(e) =>
                            setGrade({ ...grade, score: Number(e.target.value) })
                        }
                        error={!!scoreError}
                        helperText={scoreError}
                    />
                </Grid>
                {error && (
                    <Grid item xs={12}>
                        <Alert severity="error">{error}</Alert>
                    </Grid>
                )}
                <Grid item xs={12}>
                    <Button
                        variant="contained"
                        color="primary"
                        startIcon={<Add />}
                        onClick={handleSubmit}
                        disabled={loading}
                        fullWidth
                    >
                        {loading ? "Adding..." : "Add Grade"}
                    </Button>
                </Grid>
            </Grid>
        </Paper>
    );
}

export default AddGrade;

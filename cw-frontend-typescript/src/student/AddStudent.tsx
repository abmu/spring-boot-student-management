import React from "react";
import axios from "axios";
import {
    Paper,
    TextField,
    Button,
    Typography,
    Alert,
} from "@mui/material";
import { EntityModelStudent } from "../api/entityModelStudent.ts";
import { API_ENDPOINT } from "../config";

function AddStudent(props: { update: Function }) {
    const [student, setStudent] = React.useState<EntityModelStudent>({});
    const [error, setError] = React.useState<string>();

    function request() {
        axios
            .post(`${API_ENDPOINT}/students`, student)
            .then(() => {
                props.update();
                setStudent({}); // Reset form after submission
            })
            .catch((response) => {
                setError(response.message);
            });
    }

    return (
        <Paper sx={{ padding: "30px", marginTop: "20px" }} elevation={3}>
            <Typography variant="h5" gutterBottom>
                Add/Update Student
            </Typography>
            <TextField
                label="Student ID"
                fullWidth
                value={student.id || ""}
                onChange={(e) => {
                    setStudent({ ...student, id: Number(e.target.value) });
                }}
                sx={{ marginBottom: "20px" }}
            />
            <TextField
                label="Username"
                fullWidth
                value={student.username || ""}
                onChange={(e) => {
                    setStudent({ ...student, username: e.target.value });
                }}
                sx={{ marginBottom: "20px" }}
            />
            <TextField
                label="Email"
                fullWidth
                value={student.email || ""}
                onChange={(e) => {
                    setStudent({ ...student, email: e.target.value });
                }}
                sx={{ marginBottom: "20px" }}
            />
            <TextField
                label="First Name"
                fullWidth
                value={student.firstName || ""}
                onChange={(e) => {
                    setStudent({ ...student, firstName: e.target.value });
                }}
                sx={{ marginBottom: "20px" }}
            />
            <TextField
                label="Last Name"
                fullWidth
                value={student.lastName || ""}
                onChange={(e) => {
                    setStudent({ ...student, lastName: e.target.value });
                }}
                sx={{ marginBottom: "20px" }}
            />
            <Button
                onClick={request}
                variant="contained"
                color="primary"
                fullWidth
                sx={{ marginBottom: "20px" }}
            >
                Add/Update
            </Button>
            {error && <Alert severity="error">{error}</Alert>}
        </Paper>
    );
}

export default AddStudent;

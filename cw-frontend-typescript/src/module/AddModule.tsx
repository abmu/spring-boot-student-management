import React from "react";
import axios from "axios";
import {
    Paper,
    TextField,
    Switch,
    FormControlLabel,
    Button,
    Typography,
    Alert,
} from "@mui/material";
import { EntityModelModule } from "../api/entityModelModule.ts";
import { API_ENDPOINT } from "../config";

function AddModule(props: { update: Function }) {
    const [module, setModule] = React.useState<EntityModelModule>({});
    const [error, setError] = React.useState<string>();

    function request() {
        axios
            .post(`${API_ENDPOINT}/modules`, module)
            .then(() => {
                props.update();
                setModule({}); // Reset form after submission
            })
            .catch((response) => {
                setError(response.message);
            });
    }

    return (
        <Paper sx={{ padding: "30px", marginTop: "20px" }} elevation={3}>
            <Typography variant="h5" gutterBottom>
                Add/Update Module
            </Typography>
            <TextField
                label="Module Code"
                fullWidth
                value={module.code || ""}
                onChange={(e) => {
                    setModule({ ...module, code: e.target.value.toUpperCase() });
                }}
                sx={{ marginBottom: "20px" }}
            />
            <TextField
                label="Module Name"
                fullWidth
                value={module.name || ""}
                onChange={(e) => {
                    setModule({ ...module, name: e.target.value });
                }}
                sx={{ marginBottom: "20px" }}
            />
            <FormControlLabel
                control={
                    <Switch
                        checked={module.mnc ?? false}
                        onChange={(e) => {
                            setModule({ ...module, mnc: e.target.checked });
                        }}
                    />
                }
                label="MNC?"
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

export default AddModule;

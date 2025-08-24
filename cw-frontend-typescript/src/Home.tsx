import {Typography, Grid, Card, CardActionArea, CardContent, Button, Box} from "@mui/material";
import {School, People, Grade, DarkMode, LightMode} from "@mui/icons-material";
import App from "./App";
import {useThemeContext} from "./ThemeContext";
import {Link} from "react-router-dom";

function Home() {
    const {toggleTheme, mode} = useThemeContext();

    return (
        <App>
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: '80vh',
                    textAlign: 'center',
                }}
            >
                {/* Hero Section */}
                <Box
                    sx={{
                        padding: "50px 20px",
                        borderRadius: "10px",
                        marginBottom: "30px",
                    }}
                >
                    <Typography variant="h3" gutterBottom>
                        Welcome to the Student Management Portal
                    </Typography>
                    <Typography variant="subtitle1">
                        Manage your modules, students, and grades effortlessly.
                    </Typography>
                </Box>

                <Grid container spacing={3} justifyContent="center" maxWidth="800px">
                    <Grid item xs={12} sm={6} md={4}>
                        <Card
                            sx={{
                                transition: "transform 0.3s ease, box-shadow 0.3s ease",
                                "&:hover": {
                                    transform: "scale(1.05)", // Slightly enlarge the card on hover
                                    boxShadow: "0px 8px 20px rgba(0, 0, 0, 0.2)", // Add a shadow on hover
                                },
                            }}
                        >
                            <CardActionArea component={Link} to="/modules">
                                <CardContent>
                                    <School style={{fontSize: 50, color: "#1976d2"}}/>
                                    <Typography variant="h5" gutterBottom>
                                        Modules
                                    </Typography>
                                    <Typography variant="body2">
                                        View and manage all the available modules.
                                    </Typography>
                                </CardContent>
                            </CardActionArea>
                        </Card>
                    </Grid>

                    <Grid item xs={12} sm={6} md={4}>
                        <Card
                            sx={{
                                transition: "transform 0.3s ease, box-shadow 0.3s ease",
                                "&:hover": {
                                    transform: "scale(1.05)", // Slightly enlarge the card on hover
                                    boxShadow: "0px 8px 20px rgba(0, 0, 0, 0.2)", // Add a shadow on hover
                                },
                            }}
                        >
                            <CardActionArea component={Link} to="/students">
                                <CardContent>
                                    <People style={{fontSize: 50, color: "#1976d2"}}/>
                                    <Typography variant="h5" gutterBottom>
                                        Students
                                    </Typography>
                                    <Typography variant="body2">
                                        Manage student records and details.
                                    </Typography>
                                </CardContent>
                            </CardActionArea>
                        </Card>
                    </Grid>

                    <Grid item xs={12} sm={6} md={4}>
                        <Card
                            sx={{
                                transition: "transform 0.3s ease, box-shadow 0.3s ease",
                                "&:hover": {
                                    transform: "scale(1.05)", // Slightly enlarge the card on hover
                                    boxShadow: "0px 8px 20px rgba(0, 0, 0, 0.2)", // Add a shadow on hover
                                },
                            }}
                        >
                            <CardActionArea component={Link} to="/grades">
                                <CardContent>
                                    <Grade style={{fontSize: 50, color: "#1976d2"}}/>
                                    <Typography variant="h5" gutterBottom>
                                        Grades
                                    </Typography>
                                    <Typography variant="body2">
                                        Track and manage student grades efficiently.
                                    </Typography>
                                </CardContent>
                            </CardActionArea>
                        </Card>
                    </Grid>
                </Grid>
                <Box
                    sx={{
                        padding: "10px",
                        textAlign: "center",
                        marginTop: "20px",
                    }}
                >
                    <Typography variant="body2" sx={{fontWeight: "bold"}}>
                        Created by Group 15
                    </Typography>
                </Box>
            </Box>
        </App>
    );
}

export default Home;

import { AppBar, Toolbar, Button, Box } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import { useThemeContext } from "./ThemeContext";
import { DarkMode, LightMode } from "@mui/icons-material";

function NavigationBar() {
    const { toggleTheme, mode } = useThemeContext();
    const location = useLocation();

    const isActive = (path: string) => location.pathname === path;

    return (
        <AppBar position="static" sx={{ margin: "0 0 20px 0" }}>
            <Toolbar>
                <Box sx={{ display: "flex", flexGrow: 1 }}>
                    {[
                        { label: "Home", path: "/" },
                        { label: "Modules", path: "/modules" },
                        { label: "Students", path: "/students" },
                        { label: "Grades", path: "/grades" },
                    ].map((navItem) => (
                        <Button
                            key={navItem.path}
                            color="inherit"
                            component={Link}
                            to={navItem.path}
                            sx={{
                                marginRight: 2,
                                transition: "transform 0.3s ease",
                                "&:hover": { transform: "scale(1.1)" },
                                fontWeight: isActive(navItem.path) ? "bold" : "normal",
                                color: isActive(navItem.path) ? "#f1c40f" : "white", // Active link is gold/yellow
                            }}
                        >
                            {navItem.label}
                        </Button>
                    ))}
                </Box>
                <Button
                    color="inherit"
                    onClick={toggleTheme}
                    startIcon={mode === "light" ? <DarkMode /> : <LightMode />}
                    sx={{
                        transition: "transform 0.3s ease",
                        "&:hover": { transform: "scale(1.1)" },
                    }}
                >
                    Toggle {mode === "light" ? "Dark" : "Light"} Mode
                </Button>
            </Toolbar>
        </AppBar>
    );
}

export default NavigationBar;

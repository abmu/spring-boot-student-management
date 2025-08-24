import {Container, CssBaseline, Box} from "@mui/material";
import {ThemeContextProvider} from "./ThemeContext";
import NavigationBar from "./NavigationBar.tsx";
import React from "react";

function App(props: { children: React.ReactNode }) {
    return (
        <ThemeContextProvider>
            {/* Ensures global styles like background color and text color adapt to the theme */}
            <CssBaseline/>
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    height: '100vh',
                }}
            >
                <NavigationBar />
                <Container>
                    {props.children}
                </Container>
            </Box>
        </ThemeContextProvider>
    );
}

export default App;



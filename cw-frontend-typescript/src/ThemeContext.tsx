import { createContext, useContext, useState, useMemo, ReactNode } from "react";
import { createTheme, ThemeProvider, CssBaseline } from "@mui/material";

interface ThemeContextProps {
    toggleTheme: () => void;
    mode: "light" | "dark";
}

const ThemeContext = createContext<ThemeContextProps | undefined>(undefined);

export function useThemeContext() {
    const context = useContext(ThemeContext);
    if (!context) {
        throw new Error("useThemeContext must be used within a ThemeProvider");
    }
    return context;
}

export function ThemeContextProvider({ children }: { children: ReactNode }) {
    const [mode, setMode] = useState<"light" | "dark">(
        () => (localStorage.getItem("themeMode") as "light" | "dark") || "light"
    );

    const toggleTheme = () => {
        setMode((prev) => {
            const newMode = prev === "light" ? "dark" : "light";
            localStorage.setItem("themeMode", newMode);
            return newMode;
        });
    };

    const theme = useMemo(
        () =>
            createTheme({
                palette: {
                    mode,
                },
            }),
        [mode]
    );

    return (
        <ThemeContext.Provider value={{ toggleTheme, mode }}>
            <ThemeProvider theme={theme} key={mode}>
                <CssBaseline />
                {children}
            </ThemeProvider>
        </ThemeContext.Provider>
    );
}
/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  darkMode: "class",
  theme: {
    extend: {
      colors: {
        background: "#09090b",
        surface: {
          DEFAULT: "#131315",
          card: "#18181b",
          container: "#201f22",
          high: "#2a2a2c",
          highest: "#353437",
          low: "#1c1b1d",
          lowest: "#0e0e10",
        },
        primary: {
          DEFAULT: "#6366f1",
          light: "#c0c1ff",
          container: "#8083ff",
        },
        income: {
          DEFAULT: "#10b981",
          light: "#4edea3",
          container: "#00a572",
        },
        expense: {
          DEFAULT: "#f43f5e",
          light: "#ffb4ab",
          container: "#ff516a",
        },
        on: {
          surface: "#e5e1e4",
          variant: "#a1a1aa",
          muted: "#71717a",
        },
        outline: {
          DEFAULT: "#27272a",
          variant: "#3f3f46",
        }
      },
      fontFamily: {
        sans: ['"Helvetica Neue"', 'Helvetica', 'Arial', 'sans-serif'],
        roboto: ['"Helvetica Neue"', 'Helvetica', 'Arial', 'sans-serif'],
        inter: ['"Helvetica Neue"', 'Helvetica', 'Arial', 'sans-serif'],
        lato: ['"Helvetica Neue"', 'Helvetica', 'Arial', 'sans-serif'],
        jetbrains: ['"Helvetica Neue"', 'Helvetica', 'Arial', 'sans-serif'],
        helvetica: ['"Helvetica Neue"', 'Helvetica', 'Arial', 'sans-serif'],
        mono: ['"Helvetica Neue"', 'Helvetica', 'Arial', 'sans-serif'],
      },
      borderRadius: {
        'sm': '0.25rem',
        'DEFAULT': '0.375rem',
        'md': '0.5rem',
        'lg': '0.75rem',
        'xl': '1rem',
      }
    },
  },
  plugins: [],
}

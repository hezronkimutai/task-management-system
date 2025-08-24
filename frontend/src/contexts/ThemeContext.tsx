import React, { createContext, useContext, useEffect, useMemo, useState } from 'react';
import { createAppTheme } from '../theme';

type ThemeMode = 'light' | 'dark';

interface ThemeContextType {
  mode: ThemeMode;
  toggleMode: () => void;
  theme: ReturnType<typeof createAppTheme>;
}

const ThemeContext = createContext<ThemeContextType | undefined>(undefined);

const STORAGE_KEY = 'tms_theme_mode';

export const ThemeProviderCustom: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [mode, setMode] = useState<ThemeMode>(() => {
    try {
      const saved = localStorage.getItem(STORAGE_KEY) as ThemeMode | null;
      if (saved === 'light' || saved === 'dark') return saved;
    } catch (e) {
      // ignore
    }
    // default to dark to match existing app
    return 'dark';
  });

  useEffect(() => {
    try {
      localStorage.setItem(STORAGE_KEY, mode);
    } catch (e) {
      // ignore storage errors
    }
    // toggle a data attribute on html for css fallbacks
    document.documentElement.setAttribute('data-theme', mode);
  }, [mode]);

  const toggleMode = () => setMode(prev => (prev === 'dark' ? 'light' : 'dark'));

  const theme = useMemo(() => createAppTheme(mode), [mode]);

  return (
    <ThemeContext.Provider value={{ mode, toggleMode, theme }}>
      {children}
    </ThemeContext.Provider>
  );
};

export const useThemeMode = () => {
  const ctx = useContext(ThemeContext);
  if (!ctx) throw new Error('useThemeMode must be used within ThemeProviderCustom');
  return ctx;
};

export default ThemeContext;

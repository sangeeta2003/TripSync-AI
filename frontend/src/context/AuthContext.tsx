import { createContext, useContext, useEffect, useMemo, useState, type ReactNode } from 'react'
import { api } from '../lib/api'
import type { AuthResponse, User } from '../types'

interface AuthContextValue {
  user: User | null; loading: boolean
  login: (email: string, password: string) => Promise<void>
  signup: (name: string, email: string, password: string) => Promise<void>
  logout: () => void
}
const AuthContext = createContext<AuthContextValue | undefined>(undefined)

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(() => {
    const saved = localStorage.getItem('tripsync_user')
    return saved ? JSON.parse(saved) as User : null
  })
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const token = localStorage.getItem('tripsync_token')
    if (!token) { setLoading(false); return }
    api.get<User>('/api/users/me')
      .then(({ data }) => { setUser(data); localStorage.setItem('tripsync_user', JSON.stringify(data)) })
      .catch(() => { localStorage.removeItem('tripsync_token'); localStorage.removeItem('tripsync_user'); setUser(null) })
      .finally(() => setLoading(false))
  }, [])

  const save = (data: AuthResponse) => {
    const next = { id: data.userId, name: data.name, email: data.email }
    localStorage.setItem('tripsync_token', data.accessToken)
    localStorage.setItem('tripsync_user', JSON.stringify(next))
    setUser(next)
  }
  const login = async (email: string, password: string) => {
    const { data } = await api.post<AuthResponse>('/api/auth/login', { email, password })
    save(data)
  }
  const signup = async (name: string, email: string, password: string) => {
    const { data } = await api.post<AuthResponse>('/api/auth/signup', { name, email, password })
    save(data)
  }
  const logout = () => {
    void api.post('/api/auth/logout').catch(() => undefined)
    localStorage.removeItem('tripsync_token'); localStorage.removeItem('tripsync_user'); setUser(null)
    window.location.href = '/login'
  }
  const value = useMemo(() => ({ user, loading, login, signup, logout }), [user, loading])
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
export function useAuth() {
  const value = useContext(AuthContext)
  if (!value) throw new Error('useAuth must be used within AuthProvider')
  return value
}

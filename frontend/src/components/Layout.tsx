import { NavLink, Outlet, useLocation } from 'react-router-dom'
import { Bell, Compass, LayoutDashboard, LogOut, Map, Plus, Receipt, Sparkles, Users } from 'lucide-react'
import { useAuth } from '../context/AuthContext'

const links = [
  { to: '/', label: 'Overview', icon: LayoutDashboard, end: true },
  { to: '/trips', label: 'My trips', icon: Map },
  { to: '/trips/new', label: 'Create trip', icon: Plus },
  { to: '/travel', label: 'Travel search', icon: Compass },
  { to: '/ai', label: 'AI planner', icon: Sparkles },
  { to: '/notifications', label: 'Notifications', icon: Bell },
]
export default function Layout() {
  const { user, logout } = useAuth()
  const location = useLocation()
  const title = links.find(l => l.to === location.pathname)?.label || 'Trip workspace'
  return <div className="app-shell">
    <aside className="sidebar">
      <div className="brand"><div className="brand-mark">T</div><div><strong>TripSync</strong><span>AI TRAVEL PLANNER</span></div></div>
      <div className="workspace-label">WORKSPACE</div>
      <nav>{links.map(({ to, label, icon: Icon, end }) => <NavLink key={to} to={to} end={end} className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}><Icon size={18}/><span>{label}</span></NavLink>)}</nav>
      <div className="sidebar-bottom">
        <div className="user-mini"><div className="avatar">{user?.name?.[0]?.toUpperCase()}</div><div className="user-mini-info"><strong>{user?.name}</strong><span>{user?.email}</span></div></div>
        <button className="nav-link logout" onClick={logout}><LogOut size={18}/>Sign out</button>
      </div>
    </aside>
    <main className="main-area">
      <header className="topbar"><div className="mobile-brand"><div className="brand-mark">T</div><strong>TripSync</strong></div><div className="breadcrumb">Workspace <span>/</span> {title}</div><div className="topbar-user"><span className="status-dot"/> All changes synced <div className="avatar small">{user?.name?.[0]?.toUpperCase()}</div></div></header>
      <div className="page-content"><Outlet /></div>
    </main>
  </div>
}

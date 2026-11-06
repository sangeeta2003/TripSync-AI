import { Link } from 'react-router-dom'
export default function NotFound(){return <div className="screen-center"><h1>Page not found</h1><p className="muted">This page doesn't exist.</p><Link className="btn primary" to="/">Back to dashboard</Link></div>}

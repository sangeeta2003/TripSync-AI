import { useState, type FormEvent } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { Compass, Sparkles } from 'lucide-react'
import { useAuth } from '../context/AuthContext'
import { errorMessage } from '../lib/api'
import { ErrorMessage } from '../components/UI'

export default function Login() {
  const { login } = useAuth(); const navigate = useNavigate()
  const [email,setEmail]=useState(''); const [password,setPassword]=useState('')
  const [error,setError]=useState(''); const [busy,setBusy]=useState(false)
  async function submit(e:FormEvent){e.preventDefault();setError('');setBusy(true);try{await login(email,password);navigate('/')}catch(err){setError(errorMessage(err))}finally{setBusy(false)}}
  return <div className="auth-layout"><div className="auth-visual"><div className="brand light"><div className="brand-mark">T</div><div><strong>TripSync</strong><span>AI TRAVEL PLANNER</span></div></div><div className="auth-visual-copy"><div className="eyebrow light-text">TRAVEL BETTER, TOGETHER</div><h1>Every trip is better when it's <em>in sync.</em></h1><p>Plan together, track every rupee, and make group travel feel effortless.</p><div className="visual-pills"><span><Compass size={15}/> Thoughtful itineraries</span><span><Sparkles size={15}/> AI-powered planning</span></div></div><div className="visual-foot">YOUR NEXT ADVENTURE STARTS HERE.</div></div>
    <div className="auth-panel"><div className="auth-form-wrap"><div className="eyebrow">WELCOME BACK</div><h2>Sign in to TripSync</h2><p className="muted">Pick up where your next adventure begins.</p>{error&&<ErrorMessage>{error}</ErrorMessage>}<form onSubmit={submit} className="form-stack"><label className="field"><span>Email address</span><input type="email" value={email} onChange={e=>setEmail(e.target.value)} placeholder="you@example.com" required/></label><label className="field"><span>Password</span><input type="password" value={password} onChange={e=>setPassword(e.target.value)} placeholder="Enter your password" required/></label><button className="btn primary full" disabled={busy}>{busy?'Signing in...':'Sign in'}</button></form><p className="auth-switch">New to TripSync? <Link to="/signup">Create an account</Link></p></div></div></div>
}

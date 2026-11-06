import { useState, type FormEvent } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { errorMessage } from '../lib/api'
import { ErrorMessage } from '../components/UI'

export default function Signup() {
  const { signup }=useAuth();const navigate=useNavigate()
  const [name,setName]=useState('');const [email,setEmail]=useState('');const [password,setPassword]=useState('')
  const [error,setError]=useState('');const [busy,setBusy]=useState(false)
  async function submit(e:FormEvent){e.preventDefault();setError('');setBusy(true);try{await signup(name,email,password);navigate('/')}catch(err){setError(errorMessage(err))}finally{setBusy(false)}}
  return <div className="auth-layout"><div className="auth-visual"><div className="brand light"><div className="brand-mark">T</div><div><strong>TripSync</strong><span>AI TRAVEL PLANNER</span></div></div><div className="auth-visual-copy"><div className="eyebrow light-text">MAKE MEMORIES, NOT SPREADSHEETS</div><h1>Bring your whole crew <em>along.</em></h1><p>One shared space for your itinerary, group expenses and travel decisions.</p></div><div className="visual-foot">PLAN TOGETHER. GO FURTHER.</div></div>
    <div className="auth-panel"><div className="auth-form-wrap"><div className="eyebrow">GET STARTED</div><h2>Create your account</h2><p className="muted">Your next group adventure starts here.</p>{error&&<ErrorMessage>{error}</ErrorMessage>}<form onSubmit={submit} className="form-stack"><label className="field"><span>Full name</span><input value={name} onChange={e=>setName(e.target.value)} placeholder="Your name" required maxLength={120}/></label><label className="field"><span>Email address</span><input type="email" value={email} onChange={e=>setEmail(e.target.value)} placeholder="you@example.com" required/></label><label className="field"><span>Password</span><input type="password" value={password} onChange={e=>setPassword(e.target.value)} placeholder="At least 8 characters" minLength={8} required/></label><button className="btn primary full" disabled={busy}>{busy?'Creating account...':'Create account'}</button></form><p className="auth-switch">Already have an account? <Link to="/login">Sign in</Link></p></div></div></div>
}

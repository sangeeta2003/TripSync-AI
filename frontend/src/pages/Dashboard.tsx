import { useEffect, useState } from 'react'
import { ArrowUpRight, CalendarDays, Compass, Plus, Wallet } from 'lucide-react'
import { Link } from 'react-router-dom'
import { api, errorMessage } from '../lib/api'
import type { Trip } from '../types'
import { Card, EmptyState, ErrorMessage, Loading, PageHeader, money } from '../components/UI'
import { useAuth } from '../context/AuthContext'

export default function Dashboard() {
  const {user}=useAuth();const [trips,setTrips]=useState<Trip[]>([]);const [loading,setLoading]=useState(true);const [error,setError]=useState('')
  useEffect(()=>{api.get<Trip[]>('/api/trips').then(r=>setTrips(r.data)).catch(e=>setError(errorMessage(e))).finally(()=>setLoading(false))},[])
  const upcoming=trips.filter(t=>t.status!=='COMPLETED'&&t.status!=='CANCELLED')
  return <><PageHeader eyebrow="YOUR TRAVEL SPACE" title={`Good day, ${user?.name?.split(' ')[0] || 'traveller'} 👋`} subtitle="Here's what's happening with your adventures." action={<Link className="btn primary" to="/trips/new"><Plus size={17}/> Plan a trip</Link>}/>
    <div className="stat-grid"><Card><div className="stat-icon purple"><Compass/></div><div className="stat-label">Total trips</div><div className="stat-value">{loading?'—':trips.length}</div><div className="stat-foot">Your shared adventures</div></Card><Card><div className="stat-icon green"><CalendarDays/></div><div className="stat-label">Active plans</div><div className="stat-value">{loading?'—':upcoming.length}</div><div className="stat-foot">Ready for the road</div></Card><Card><div className="stat-icon orange"><Wallet/></div><div className="stat-label">Trip workspace</div><div className="stat-value">Together</div><div className="stat-foot">Budgets, expenses & balances</div></Card></div>
    <div className="section-heading"><div><h2>Your trips</h2><p>Pick up where the planning happens.</p></div><Link className="text-link" to="/trips">View all <ArrowUpRight size={15}/></Link></div>
    {error&&<ErrorMessage>{error}</ErrorMessage>}{loading?<Loading/>:trips.length===0?<Card><EmptyState title="Your first adventure awaits" text="Create a trip and invite your favourite people to start planning together."/><div className="center"><Link to="/trips/new" className="btn primary"><Plus size={16}/> Create your first trip</Link></div></Card>:<div className="trip-grid">{trips.slice(0,3).map(t=><TripCard key={t.id} trip={t}/>)}</div>}
  </>
}
export function TripCard({trip}:{trip:Trip}) {
  return <Link to={`/trips/${trip.id}`} className="trip-card"><div className="trip-cover"><div className="cover-orb one"/><div className="cover-orb two"/><span className="trip-status">{trip.status}</span><Compass size={30}/></div><div className="trip-card-body"><div className="trip-destination">{trip.destination}</div><h3>{trip.title}</h3><div className="trip-meta"><span><CalendarDays size={14}/>{trip.startDate} – {trip.endDate}</span><span>{money(trip.budget,trip.currency)}</span></div></div></Link>
}

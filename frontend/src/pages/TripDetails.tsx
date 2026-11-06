import { useEffect,useState } from 'react'
import { CalendarDays,ClipboardList,Receipt,Users,Compass } from 'lucide-react'
import { Link,useParams } from 'react-router-dom'
import { api,errorMessage } from '../lib/api'
import type { Trip,Member } from '../types'
import { Card,ErrorMessage,Loading,PageHeader,money } from '../components/UI'
export default function TripDetails(){
 const {tripId=''}=useParams();const [trip,setTrip]=useState<Trip|null>(null);const [members,setMembers]=useState<Member[]>([]);const [error,setError]=useState('');const [loading,setLoading]=useState(true)
 useEffect(()=>{Promise.all([api.get<Trip>(`/api/trips/${tripId}`),api.get<Member[]>(`/api/trips/${tripId}/members`)]).then(([t,m])=>{setTrip(t.data);setMembers(m.data)}).catch(e=>setError(errorMessage(e))).finally(()=>setLoading(false))},[tripId])
 if(loading)return <Loading/>;if(error)return <ErrorMessage>{error}</ErrorMessage>;if(!trip)return null
 return <><PageHeader eyebrow="TRIP WORKSPACE" title={trip.title} subtitle={trip.destination}/><div className="detail-hero"><div className="detail-hero-icon"><Compass size={34}/></div><div className="detail-hero-info"><span className="eyebrow light-text">{trip.status}</span><h2>{trip.destination}</h2><p><CalendarDays size={15}/>{trip.startDate} — {trip.endDate}</p></div><div className="detail-budget"><span>GROUP BUDGET</span><strong>{money(trip.budget,trip.currency)}</strong></div></div><div className="quick-grid"><Link to={`/trips/${tripId}/itinerary`} className="quick-card"><ClipboardList/><strong>Itinerary</strong><span>Plan your days</span></Link><Link to={`/trips/${tripId}/expenses`} className="quick-card"><Receipt/><strong>Expenses</strong><span>Track every rupee</span></Link><Link to="/travel" className="quick-card"><Compass/><strong>Travel search</strong><span>Find tickets & stays</span></Link><div className="quick-card"><Users/><strong>{members.length} members</strong><span>Group members</span></div></div><Card><div className="section-heading"><div><h2>Trip members</h2><p>People in this adventure.</p></div></div><div className="member-list">{members.map(m=><div className="member-row" key={m.id}><div className="avatar">{m.name[0]?.toUpperCase()}</div><div className="member-info"><strong>{m.name}</strong><span>{m.email}</span></div><span className="role-tag">{m.role}</span></div>)}</div></Card></>
}

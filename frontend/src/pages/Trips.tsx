import { useEffect, useState } from 'react'
import { Plus } from 'lucide-react'
import { Link } from 'react-router-dom'
import { api,errorMessage } from '../lib/api'
import type { Trip } from '../types'
import { Card,EmptyState,ErrorMessage,Loading,PageHeader } from '../components/UI'
import { TripCard } from './Dashboard'
export default function Trips(){
 const [trips,setTrips]=useState<Trip[]>([]);const [loading,setLoading]=useState(true);const [error,setError]=useState('')
 useEffect(()=>{api.get<Trip[]>('/api/trips').then(r=>setTrips(r.data)).catch(e=>setError(errorMessage(e))).finally(()=>setLoading(false))},[])
 return <><PageHeader eyebrow="ALL ADVENTURES" title="My trips" subtitle="All your group travel plans, in one place." action={<Link className="btn primary" to="/trips/new"><Plus size={17}/> New trip</Link>}/>{error&&<ErrorMessage>{error}</ErrorMessage>}{loading?<Loading/>:trips.length?<div className="trip-grid">{trips.map(t=><TripCard key={t.id} trip={t}/>)}</div>:<Card><EmptyState title="No trips yet" text="Create a trip to start building your next adventure."/></Card>}</>
}

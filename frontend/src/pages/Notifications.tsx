import { useEffect,useState } from 'react'
import { Bell,Check } from 'lucide-react'
import { api,errorMessage } from '../lib/api'
import type { Notification } from '../types'
import { Card,EmptyState,ErrorMessage,Loading,PageHeader } from '../components/UI'
export default function Notifications(){
 const [items,setItems]=useState<Notification[]>([]);const [loading,setLoading]=useState(true);const [error,setError]=useState('')
 const load=()=>api.get<Notification[]>('/api/notifications').then(r=>setItems(r.data)).catch(e=>setError(errorMessage(e))).finally(()=>setLoading(false))
 useEffect(()=>{load()},[])
 async function mark(id:string){try{await api.patch(`/api/notifications/${id}/read`);load()}catch(e){setError(errorMessage(e))}}
 return <><PageHeader eyebrow="STAY IN THE LOOP" title="Notifications" subtitle="Updates from your trips and travel group."/>{error&&<ErrorMessage>{error}</ErrorMessage>}{loading?<Loading/>:<Card>{items.length===0?<EmptyState title="You're all caught up" text="Trip updates and group activity will appear here."/>:items.map(n=><div className={`notification-row ${n.read?'read':''}`} key={n.id}><div className="notification-icon"><Bell size={17}/></div><div className="notification-copy"><strong>{n.title}</strong><p>{n.body}</p><span>{new Date(n.createdAt).toLocaleString()}</span></div>{!n.read&&<button className="icon-btn" title="Mark as read" onClick={()=>mark(n.id)}><Check size={17}/></button>}</div>)}</Card>}</>
}

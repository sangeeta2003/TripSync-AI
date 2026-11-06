import { Navigate, Route, Routes } from 'react-router-dom'
import ProtectedRoute from './components/ProtectedRoute'
import Layout from './components/Layout'
import Login from './pages/Login'
import Signup from './pages/Signup'
import Dashboard from './pages/Dashboard'
import Trips from './pages/Trips'
import CreateTrip from './pages/CreateTrip'
import TripDetails from './pages/TripDetails'
import Itinerary from './pages/Itinerary'
import Expenses from './pages/Expenses'
import AiPlanner from './pages/AiPlanner'
import TravelSearch from './pages/TravelSearch'
import Notifications from './pages/Notifications'
import NotFound from './pages/NotFound'

export default function App() {
  return <Routes>
    <Route path="/login" element={<Login/>}/>
    <Route path="/signup" element={<Signup/>}/>
    <Route element={<ProtectedRoute/>}>
      <Route element={<Layout/>}>
        <Route index element={<Dashboard/>}/>
        <Route path="/trips" element={<Trips/>}/>
        <Route path="/trips/new" element={<CreateTrip/>}/>
        <Route path="/trips/:tripId" element={<TripDetails/>}/>
        <Route path="/trips/:tripId/itinerary" element={<Itinerary/>}/>
        <Route path="/trips/:tripId/expenses" element={<Expenses/>}/>
        <Route path="/ai" element={<AiPlanner/>}/>
        <Route path="/travel" element={<TravelSearch/>}/>
        <Route path="/notifications" element={<Notifications/>}/>
      </Route>
    </Route>
    <Route path="/404" element={<NotFound/>}/>
    <Route path="*" element={<Navigate to="/404" replace/>}/>
  </Routes>
}

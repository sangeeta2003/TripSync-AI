export interface User { id: string; name: string; email: string }
export interface AuthResponse { accessToken: string; userId: string; name: string; email: string }
export interface Trip {
  id: string; title: string; destination: string; startDate: string; endDate: string
  budget: number; currency: string; status: string; ownerId: string
}
export interface Member { id: string; userId: string; name: string; email: string; role: string; status: string }
export interface ItineraryItem {
  id: string; dayNumber: number; title: string; description?: string; location?: string
  startTime?: string; estimatedCost: number; sortOrder: number
}
export interface Expense {
  id: string; title: string; category: string; amount: number; currency: string
  paidById: string; paidByName: string; createdAt: string
}
export interface Balance { userId: string; name: string; netBalance: number }
export interface Settlement {
  id: string; fromUserId: string; fromName: string; toUserId: string; toName: string
  amount: number; status: string
}
export interface TravelSearch {
  category: string; query: string; providerName: string; bookingSearchUrl: string; availabilityNote: string
}
export interface AiResponse { recommendation: string; aiGenerated: boolean }
export interface ChatMessage {
  id: string; senderId: string; senderName: string; content: string; sentAt: string
}
export interface Notification {
  id: string; type: string; title: string; body: string; read: boolean; createdAt: string
}

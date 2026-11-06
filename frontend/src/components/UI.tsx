import type { ReactNode } from 'react'
import { AlertCircle, CheckCircle2 } from 'lucide-react'

export function PageHeader({ eyebrow, title, subtitle, action }: { eyebrow?: string; title: string; subtitle?: string; action?: ReactNode }) {
  return <div className="page-header"><div>{eyebrow && <div className="eyebrow">{eyebrow}</div>}<h1>{title}</h1>{subtitle && <p>{subtitle}</p>}</div>{action && <div className="header-action">{action}</div>}</div>
}
export function Card({ children, className = '' }: { children: ReactNode; className?: string }) {
  return <section className={`card ${className}`}>{children}</section>
}
export function Loading() { return <div className="inline-loading"><div className="spinner"/>Loading...</div> }
export function ErrorMessage({ children }: { children: ReactNode }) { return <div className="alert error"><AlertCircle size={17}/>{children}</div> }
export function SuccessMessage({ children }: { children: ReactNode }) { return <div className="alert success"><CheckCircle2 size={17}/>{children}</div> }
export function EmptyState({ title, text }: { title: string; text: string }) { return <div className="empty-state"><div className="empty-icon">✦</div><strong>{title}</strong><p>{text}</p></div> }
export function Field({ label, children }: { label: string; children: ReactNode }) { return <label className="field"><span>{label}</span>{children}</label> }
export function money(amount: number, currency = 'INR') {
  return new Intl.NumberFormat('en-IN', { style: 'currency', currency, maximumFractionDigits: 2 }).format(amount || 0)
}

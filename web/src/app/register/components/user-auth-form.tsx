"use client"

import * as React from "react"

import { cn } from "@/lib/utils"
import { Label } from "@radix-ui/react-label"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { useRouter } from 'next/navigation';

interface UserAuthFormProps extends React.HTMLAttributes<HTMLDivElement> {}

export function UserAuthForm({ className, ...props }: UserAuthFormProps) {
  const [isLoading, setIsLoading] = React.useState<boolean>(false)
  const [name, setName] = React.useState<string>("")
  const [email, setEmail] = React.useState<string>("")
  const [numberPhone, setNumberPhone] = React.useState<string>("")
  const router = useRouter()

  async function onSubmit(event: React.SyntheticEvent) {
    event.preventDefault()
    setIsLoading(true)

    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/users`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        body: JSON.stringify({ name, email, numberPhone }),
      });
    
      if (!response.ok) {
        throw new Error('Erro ao enviar os dados');
      }
    
      const data = await response.json();  
      localStorage.setItem('libraryUserId', JSON.stringify(data.id));


      router.push("/book")
      
    } catch (error) {
      console.error('Erro ao enviar dados:', error);
    } finally {
      setIsLoading(false);
    }

    setTimeout(() => {
      setIsLoading(false)
    }, 3000)
  }

  return (
    <div className={cn("grid gap-6", className)} {...props}>
      <form onSubmit={onSubmit}>
        <div className="grid gap-2">
          <div className="grid gap-1">
            <Label className="sr-only" htmlFor="name">
              Nome completo
            </Label>
            <Input
              id="name"
              placeholder="Nome completo"
              type="txt"
              autoCapitalize="none"
              autoCorrect="off"
              value={name}
              onChange={(e) => setName(e.target.value)}
              disabled={isLoading}
            />
            <Label className="sr-only" htmlFor="email">
              Email
            </Label>
            <Input
              id="email"
              placeholder="name@example.com"
              type="email"
              autoCapitalize="none"
              autoComplete="email"
              autoCorrect="off"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              disabled={isLoading}
            />
            <Label className="sr-only" htmlFor="numberPhone">
              Numero de telefone
            </Label>
            <Input
              id="numberPhone"
              placeholder="(44) 91234-5678"
              type="tel"
              autoCapitalize="none"
              autoCorrect="off"
              value={numberPhone}
              onChange={(e) => setNumberPhone(e.target.value)}
              disabled={isLoading}
            />
          </div>
          <Button disabled={isLoading}>
            Prosseguir
          </Button>
        </div>
      </form>
      <div className="relative">
        <div className="absolute inset-0 flex items-center">
          <span className="w-full border-t" />
        </div>
      </div>
    </div>
  )
}
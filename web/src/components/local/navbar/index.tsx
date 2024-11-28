import Link from "next/link"

const Navbar = ({ title = "Titulo", libraryUserId = "" }) => {
  return (
    <nav className="bg-gray-800 p-4 shadow-md">
      <div className="container mx-auto flex justify-between items-center">
        <div className="text-white font-bold text-lg">{title}</div>
        <ul className="flex space-x-4 text-gray-300 items-center text-sm font-medium">
          <li>
            <Link href="/">Meus livros</Link>
          </li>
          <li>
            <Link href={`/book/${libraryUserId}`} aria-label="Recomendações">Recomendações</Link>
          </li>
          <li>
            <Link href="/book">Sair</Link>
          </li>
        </ul>
      </div>
    </nav>
  )
}

export default Navbar

"use client";

import Navbar from "@/components/local/navbar";
import { Button } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import Link from "next/link";
import { useEffect, useState } from "react";

type Book = {
  id: string
  title: string
  author: string
  isbn: string
  publishedAt: string
  category: string
  active: boolean
}

type BookToLoan = {
  id: string
};

type LibraryUser = {
  id: string
}

type BookLibraryUser = {
  book: BookToLoan
  libraryUser: LibraryUser
}

const Book = () => {
  const [books, setBooks] = useState<Book[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [libraryUserId, setLibraryUserId] = useState("");
  const [newBook, setNewBook] = useState<Book>({
    id: "",
    title: "",
    author: "",
    isbn: "",
    publishedAt: "",
    category: "",
    active: true,
  });

  useEffect(() => {
    async function fetchBooks() {
      const userId = localStorage.getItem("libraryUserId");
      if (userId) {
        const cleanUserId = userId.replace(/^"|"$/g, "");
        setLibraryUserId(cleanUserId);
      } else {
        setLibraryUserId("");
      }

      try {
        const response = await fetch("http://localhost:9090/books");
        const data: Book[] = await response.json();
        setBooks(data);
      } catch (error) {
        console.error(error);
      }
    }
    fetchBooks();
  }, []);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setNewBook({ ...newBook, [name]: value });
  };

  const defaultHour = "00:00";

  const [datePart] = newBook.publishedAt.split("T");
  const formattedPublishedAt = `${datePart}T${defaultHour}`;

  const formattedBook = {
    ...newBook,
    publishedAt: formattedPublishedAt
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:9090/books", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formattedBook),
      });

      if (!response.ok) {
        throw new Error("Erro ao cadastrar o livro");
      }

      const addedBook = await response.json();
      setBooks((prevBooks) => [...prevBooks, addedBook]);
      closeModal();
    } catch (error) {
      console.error("Erro ao cadastrar o livro:", error);
    }
  }

  const handleLoanBook = async (id: string) => {
    try {
      const example: BookLibraryUser = {
        book: {
            id: id
        },
        libraryUser: {
            id: libraryUserId
        }
    };

      if (!id) {
        throw new Error('ID inválido');
      }

      const response = await fetch(`http://localhost:9090/loans`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(example),
      });
  
      if (!response.ok) {
        throw new Error('Erro ao emprestar o livro');
      }

      setBooks((prevBooks) => prevBooks.filter((b) => b.id !== id));
  
    } catch (error) {
      console.error('Erro:', error);
    }
  };

  return (
    <>
      <Navbar title="Livros" libraryUserId={libraryUserId} />
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Nome</TableHead>
            <TableHead>Autor</TableHead>
            <TableHead>Categoria</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {books.map((book) => (
            <TableRow key={book.id}>
              <TableCell className="font-medium">{book.title}</TableCell>
              <TableCell>{book.author}</TableCell>
              <TableCell>{book.category}</TableCell>
              <TableCell>
                <Button
                  className="hover:bg-white hover:text-gray-600 text-white font-bold py-2 px-4 rounded-full"
                  onClick={() => handleLoanBook(book.id)}
                >
                  Emprestar
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Button
        className="fixed bottom-4 right-4 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded-full"
        onClick={openModal}
      >
        Cadastrar
      </Button>

      {isModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded-lg shadow-lg max-w-lg w-full">
            <h2 className="text-lg font-bold mb-4">Cadastrar Livro</h2>
            <form onSubmit={handleSubmit}>
              <div className="mb-4">
                <label className="block text-sm font-medium">Título</label>
                <input
                  type="text"
                  name="title"
                  value={newBook.title}
                  onChange={handleInputChange}
                  className="w-full border border-gray-300 p-2 rounded"
                  required
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium">Autor</label>
                <input
                  type="text"
                  name="author"
                  value={newBook.author}
                  onChange={handleInputChange}
                  className="w-full border border-gray-300 p-2 rounded"
                  required
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium">ISBN</label>
                <input
                  type="text"
                  name="isbn"
                  value={newBook.isbn}
                  onChange={handleInputChange}
                  className="w-full border border-gray-300 p-2 rounded"
                  required
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium">
                  Data de Publicação
                </label>
                <input
                  type="date"
                  name="publishedAt"
                  value={newBook.publishedAt.split("T")[0]} // Para exibir apenas a data
                  onChange={handleInputChange}
                  className="w-full border border-gray-300 p-2 rounded"
                  required
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium">Categoria</label>
                <input
                  type="text"
                  name="category"
                  value={newBook.category}
                  onChange={handleInputChange}
                  className="w-full border border-gray-300 p-2 rounded"
                  required
                />
              </div>
              <div className="flex justify-end">
                <Button type="button" className="mr-4" onClick={closeModal}>
                  Cancelar
                </Button>
                <Button type="submit">Salvar</Button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  );
};

export default Book;

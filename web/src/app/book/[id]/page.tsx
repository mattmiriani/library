"use client";

import Navbar from "@/components/local/navbar";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useEffect, useState } from "react";

type Book = {
  title: string;
  author: string;
  isbn: string;
  publishedAt: string;
  category: string;
  active: boolean;
};

const Book = () => {
  const [books, setBooks] = useState<Book[]>([]);

  useEffect(() => {
    async function fetchBooks() {
      const userId = localStorage.getItem("libraryUserId")?.replace(/^"|"$/g, '')
      if (!userId) {
        console.error("libraryUserId não encontrado");
        return;
      }

      try {
        const response = await fetch(`http://localhost:9090/books/recomendations/${userId}`);
        const data: Book[] = await response.json();
        setBooks(data);
      } catch (error) {
        console.error(error);
      }
    }
    fetchBooks();
  }, []);

  return (
    <>
      <Navbar title="Livros"/>
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
            <TableRow key={book.isbn}>
              <TableCell className="font-medium">{book.title}</TableCell>
              <TableCell>{book.author}</TableCell>
              <TableCell>{book.category}</TableCell>
              <TableCell className="font-medium text-center text-amber-500 italic tracking-wide">RECOMENDADO</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </>
  );
};

export default Book;

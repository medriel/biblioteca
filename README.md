# Biblioteca

<h2>Tarefa proposta durante a disciplina de Laboratório de Programação e Software Orinetado a Objetos, do curso de Engenharia da Computação, na Faculdade Católica Salesiana de Macaé</h2>

A Biblioteca de uma faculdade deseja informatizar o controle de empréstimos de seus livros. Para cada livro cadastrado a biblioteca pode possuir um ou mais exemplares para empréstimo. Tanto os livros quanto seus exemplares devem estar cadastrados no sistema para que possam ser emprestados e sobre os livros são guardados os seguintes dados: Código, Título, Autor e Ano. Para os exemplares apenas são guardados o código correspondente e a data de aquisição. O responsável pelo cadastramento dos livros e dos exemplares é o bibliotecário e somente pode ser cadastrado exemplares para livros já cadastrados.  

 

O atendente da Biblioteca somente empresta exemplares de livros a leitores previamente cadastrados para os quais são armazenados os seguintes dados (Código, Nome, Endereço, Telefone). Só existem dois tipos de leitores, os professores e os alunos. Dos professores, é armazenada a disciplina que leciona, enquanto que dos alunos é armazenada seu número de matrícula. Quem cadastra os leitores é o atendente. 

 

Cada livro pode possui zero ou mais autores, e cada autor pode ter escrito um ou mais livros. O bibliotecário insere no sistema o código, nome e nacionalidade dos autores. 

 

Atenção que somente podem ser cadastrados livros se os autores já estiverem no sistema. 

 

Os livros devem ser devolvidos pelos leitores ao atendente em no máximo quinze dias a partir da data de empréstimo se forem alunos e trinta dias se forem professores. Portanto a data de devolução não pode ultrapassar estes prazos e, para controlar os atrasos, diariamente o bibliotecário tira uma listagem dos leitores em atraso. 

 
De posse do problema citado, desenvolva o sistema, fazendo: 

1) Diagrama de classes 

2) Diagrama de caso de uso 

3) Classes mapeadas para o Java 

4) Testes das regras de negócio usando o JUnit

5) Interface gráfica usando JavaFX 

6) Persistência usando PostgreSQL


O diagrama abaixo ilustra os casos de uso da aplicação.

![image](https://user-images.githubusercontent.com/74268252/123174484-919a0c00-d456-11eb-847c-06f31497363d.png)

O diagrama de classes abaixo representa o sistema desenvolvido.

![image](https://user-images.githubusercontent.com/74268252/123174561-b4c4bb80-d456-11eb-8dc9-b8c5202f4d6d.png)

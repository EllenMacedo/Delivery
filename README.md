Ellen Cristhyna e Kimi Junger

Versao do sistema de Delivery + CR2.
A biblioteca Auditoria foi adicionada ao JitPack: https://jitpack.io/#EllenMacedo/Auditoria/v1.0.0

Biblioteca disponivel no github:
https://github.com/EllenMacedo/Auditoria

git clone do projeto:
git clone https://github.com/EllenMacedo/Delivery.git

dependencia utilizada:
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  
<dependency>
	    <groupId>com.github.EllenMacedo</groupId>
	    <artifactId>Auditoria</artifactId>
	    <version>v1.0.0</version>
	</dependency>


Descrição do Projeto:
Foi criado uma biblioteca Maven independente que registra logs para o sistema de Delivery, JSONL, CSV, XML, na auditoria doi feito uma interface comum para que seja possivel alterar o formato de log, de forma mutualmente exclusiva. A classe pedido foi alterada para receber um codigoPedido, que identifica o pedido. A biblioteca Auditoria esta disponivel no JitPack.

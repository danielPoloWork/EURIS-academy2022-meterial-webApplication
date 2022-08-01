package it.euris.libreria.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "libri")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Libri {
	
	public static final String FK_COLUMN = "autore";
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String titolo;
	
	@ManyToOne
	@JoinColumn(name = "idautore")
	private Autori autore;
	
	@Column
	private String isbn;
	
}

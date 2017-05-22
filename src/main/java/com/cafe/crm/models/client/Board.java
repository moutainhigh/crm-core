package com.cafe.crm.models.client;

import javax.persistence.*;

@Entity
@Table(name = "boards")
public class Board {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	private String name;

	public Board() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Board board = (Board) o;

		if (id != null ? !id.equals(board.id) : board.id != null) return false;
		return name != null ? name.equals(board.name) : board.name == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}

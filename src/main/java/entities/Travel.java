package entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="travel")
@Data

public class Travel {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="bot")
	private Bot bot;

	@ManyToOne
	@JoinColumn(name="from_loc")
	private Location from;
	
	@ManyToOne
	@JoinColumn(name="to_loc")
	private Location to;
	
	@Column(name="start_date")
	private LocalDate start;
	
	@Column(name="end_date")
	private LocalDate end;
	
}

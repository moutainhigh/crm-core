package com.cafe.crm.models.client;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class TimerOfPause {

	@Id
	@GeneratedValue
	private Long id;

	private Long idOfCalculate;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Long wholeTimePause;

	public TimerOfPause() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdOfCalculate() {
		return idOfCalculate;
	}

	public void setIdOfCalculate(Long idOfCalculate) {
		this.idOfCalculate = idOfCalculate;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Long getWholeTimePause() {
		return wholeTimePause;
	}

	public void setWholeTimePause(Long wholeTimePause) {
		this.wholeTimePause = wholeTimePause;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TimerOfPause that = (TimerOfPause) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
		if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
		return wholeTimePause != null ? wholeTimePause.equals(that.wholeTimePause) : that.wholeTimePause == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
		result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
		result = 31 * result + (wholeTimePause != null ? wholeTimePause.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "TimerOfPause{" +
				"id=" + id +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", wholeTimePause=" + wholeTimePause +
				'}';
	}
}

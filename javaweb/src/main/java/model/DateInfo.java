package model;

public class DateInfo {
	int dayOfMonth;//해당 달의 총 날짜 
	int week;//해당 달이 총 몇주인지 
	int yoil; //해당 달이 시작하는 요일이 몇번째인지 
	int year;
	int month;
	
	
	public DateInfo(int year, int month, int dayOfMonth, int week, int yoil) {
		this.dayOfMonth = dayOfMonth;
		this.week = week;
		this.yoil = yoil;
		this.year = year;
		this.month = month;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getYoil() {
		return yoil;
	}
	public void setYoil(int yoil) {
		this.yoil = yoil;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public int getCorrectMonth() {
		return getMonth() + 1;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "DateInfo [dayOfMonth=" + dayOfMonth + ", week=" + week
				+ ", yoil=" + yoil + ", year=" + year + ", month=" + month
				+ "]";
	}

}

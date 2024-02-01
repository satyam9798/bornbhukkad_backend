package com.bornbhukkad.merchant.dto;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "kirana_locations")
public class KiranaLocationDto {
    private String locationId;
    private LocationTime time;
    private String gps;
    private Address address;

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public LocationTime getTime() {
		return time;
	}

	public void setTime(LocationTime time) {
		this.time = time;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}


	private Circle circle;
    private List<Tag> tags;

     

    // Inner classes for nested structures
    public static class LocationTime {
        public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public Instant getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Instant timestamp) {
			this.timestamp = timestamp;
		}
		public Range getRange() {
			return range;
		}
		public void setRange(Range range) {
			this.range = range;
		}
		public String getDays() {
			return days;
		}
		public void setDays(String days) {
			this.days = days;
		}
		public Schedule getSchedule() {
			return schedule;
		}
		public void setSchedule(Schedule schedule) {
			this.schedule = schedule;
		}
		private String label;
        private Instant timestamp;
        private Range range;
        private String days;
        private Schedule schedule;

         
    }

    public static class Range {
        private String start;
        public String getStart() {
			return start;
		}
		public void setStart(String start) {
			this.start = start;
		}
		public String getEnd() {
			return end;
		}
		public void setEnd(String end) {
			this.end = end;
		}
		private String end;

         
    }

    public static class Schedule {
        private List<String> holidays;
        public List<String> getHolidays() {
			return holidays;
		}
		public void setHolidays(List<String> holidays) {
			this.holidays = holidays;
		}
		public String getFrequency() {
			return frequency;
		}
		public void setFrequency(String frequency) {
			this.frequency = frequency;
		}
		public List<String> getTimes() {
			return times;
		}
		public void setTimes(List<String> times) {
			this.times = times;
		}
		private String frequency;
        private List<String> times;

         
    }

    public static class Address {
        private String locality;
        private String street;
        private String city;
        private String area_code;
        private String state;
		public String getLocality() {
			return locality;
		}
		public void setLocality(String locality) {
			this.locality = locality;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getArea_code() {
			return area_code;
		}
		public void setArea_code(String area_code) {
			this.area_code = area_code;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}

         
    }

    public static class Circle {
        private String gps;
        private Radius radius;
		public String getGps() {
			return gps;
		}
		public void setGps(String gps) {
			this.gps = gps;
		}
		public Radius getRadius() {
			return radius;
		}
		public void setRadius(Radius radius) {
			this.radius = radius;
		}

         
    }

    public static class Radius {
        private String unit;
        private String value;
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}

         
    }

    public static class Tag {
        private String code;
        public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public List<Item> getList() {
			return list;
		}
		public void setList(List<Item> list) {
			this.list = list;
		}
		private List<Item> list;

         
    }

    public static class Item {
        private String code;
        private String value;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}

         
    }
}

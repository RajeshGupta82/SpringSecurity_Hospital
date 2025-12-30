package com.rajesh.hospital.hospitalmanagement.dto;

import com.rajesh.hospital.hospitalmanagement.entity.type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class BloodGroupCountResponseEntity {

    private BloodGroupType bloodGroupType;
    private Long count;
	public BloodGroupType getBloodGroupType() {
		return bloodGroupType;
	}
	public void setBloodGroupType(BloodGroupType bloodGroupType) {
		this.bloodGroupType = bloodGroupType;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public BloodGroupCountResponseEntity(BloodGroupType bloodGroupType, Long count) {
		super();
		this.bloodGroupType = bloodGroupType;
		this.count = count;
	}
	public BloodGroupCountResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
}

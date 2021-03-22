/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author creative
 */
@Entity
@Table(name = "attendance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a"),
    @NamedQuery(name = "Attendance.findByAttendanceId", query = "SELECT a FROM Attendance a WHERE a.attendanceId = :attendanceId"),
    @NamedQuery(name = "Attendance.findByAttendanceDate", query = "SELECT a FROM Attendance a WHERE a.attendanceDate = :attendanceDate"),
    @NamedQuery(name = "Attendance.findByAttendanceTime", query = "SELECT a FROM Attendance a WHERE a.attendanceTime = :attendanceTime"),
    @NamedQuery(name = "Attendance.findByAttendanceType", query = "SELECT a FROM Attendance a WHERE a.attendanceType = :attendanceType")})
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "attendance_id")
    private String attendanceId;
    @Basic(optional = false)
    @Column(name = "attendance_date")
    @Temporal(TemporalType.DATE)
    private Date attendanceDate;
    @Basic(optional = false)
    @Column(name = "attendance_time")
    @Temporal(TemporalType.TIME)
    private Date attendanceTime;
    @Basic(optional = false)
    @Lob
    @Column(name = "attendance_remark")
    private String attendanceRemark;
    @Basic(optional = false)
    @Lob
    @Column(name = "attendance_attachment")
    private String attendanceAttachment;
    @Basic(optional = false)
    @Column(name = "attendance_type")
    private String attendanceType;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users userId;
    @JoinColumn(name = "attendance_status_id", referencedColumnName = "attendance_status_id")
    @ManyToOne(optional = false)
    private AttendanceStatus attendanceStatusId;

    public Attendance() {
    }

    public Attendance(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Attendance(String attendanceId, Date attendanceDate, Date attendanceTime, String attendanceRemark, String attendanceAttachment, String attendanceType) {
        this.attendanceId = attendanceId;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.attendanceRemark = attendanceRemark;
        this.attendanceAttachment = attendanceAttachment;
        this.attendanceType = attendanceType;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Date getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(Date attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public String getAttendanceRemark() {
        return attendanceRemark;
    }

    public void setAttendanceRemark(String attendanceRemark) {
        this.attendanceRemark = attendanceRemark;
    }

    public String getAttendanceAttachment() {
        return attendanceAttachment;
    }

    public void setAttendanceAttachment(String attendanceAttachment) {
        this.attendanceAttachment = attendanceAttachment;
    }

    public String getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public AttendanceStatus getAttendanceStatusId() {
        return attendanceStatusId;
    }

    public void setAttendanceStatusId(AttendanceStatus attendanceStatusId) {
        this.attendanceStatusId = attendanceStatusId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attendanceId != null ? attendanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendance)) {
            return false;
        }
        Attendance other = (Attendance) object;
        if ((this.attendanceId == null && other.attendanceId != null) || (this.attendanceId != null && !this.attendanceId.equals(other.attendanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.HadirApp.MasterManagement.entity.Attendance[ attendanceId=" + attendanceId + " ]";
    }
    
}

package com.example.infinitylabs.dynamictrackerapp.request_response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"site_id",
"project_id",
"circle_id",
"district_id",
"notification_group_id",
"site_name",
"site_address",
"pin_code",
"site_latitude",
"site_longitude",
"country",
"state",
"district",
"city",
"zone",
"hub_code",
"proposed_equipment",
"shelter_size",
"rack_available",
"available_rack_size",
"required_rack_size",
"installation_start_u_position_bottom",
"manned_site",
"key_location",
"key_incharge_name",
"key_incharge_number",
"ring_or_linear",
"media_availability",
"dc_power_availability",
"customer_dcdb_mcb_availability",
"customer_dcdb_mcb_rating",
"rack_dcdb_mcb_availability",
"rack_dcdb_mcb_rating",
"proposed_rack_to_dcdb_distance",
"ground_bar_available",
"proposed_rack_to_ground_bar_distance",
"termination_1_type",
"termination_1_distance",
"termination_2_type",
"termination_2_distance",
"termination_3_type",
"termination_3_distance",
"termination_4_type",
"termination_4_distance",
"migration_required",
"site_engineer_name",
"site_engineer_number",
"site_engineer_email",
"site_in_charge_name",
"site_in_charge_number",
"site_in_charge_email",
"site_manager_name",
"site_manager_number",
"site_manager_email",
"escalation_manager_name",
"escalation_manager_number",
"escalation_manager_email",
"status",
"site_status",
"created_by",
"created_at",
"timestamp",
"delete_flag",
"date"
})
public class Data {

@JsonProperty("id")
private String id;
@JsonProperty("site_id")
private String siteId;
@JsonProperty("project_id")
private String projectId;
@JsonProperty("circle_id")
private String circleId;
@JsonProperty("district_id")
private String districtId;
@JsonProperty("notification_group_id")
private String notificationGroupId;
@JsonProperty("site_name")
private String siteName;
@JsonProperty("site_address")
private String siteAddress;
@JsonProperty("pin_code")
private String pinCode;
@JsonProperty("site_latitude")
private String siteLatitude;
@JsonProperty("site_longitude")
private String siteLongitude;
@JsonProperty("country")
private String country;
@JsonProperty("state")
private String state;
@JsonProperty("district")
private String district;
@JsonProperty("city")
private String city;
@JsonProperty("zone")
private String zone;
@JsonProperty("hub_code")
private String hubCode;
@JsonProperty("proposed_equipment")
private String proposedEquipment;
@JsonProperty("shelter_size")
private String shelterSize;
@JsonProperty("rack_available")
private String rackAvailable;
@JsonProperty("available_rack_size")
private String availableRackSize;
@JsonProperty("required_rack_size")
private String requiredRackSize;
@JsonProperty("installation_start_u_position_bottom")
private String installationStartUPositionBottom;
@JsonProperty("manned_site")
private String mannedSite;
@JsonProperty("key_location")
private String keyLocation;
@JsonProperty("key_incharge_name")
private String keyInchargeName;
@JsonProperty("key_incharge_number")
private String keyInchargeNumber;
@JsonProperty("ring_or_linear")
private String ringOrLinear;
@JsonProperty("media_availability")
private String mediaAvailability;
@JsonProperty("dc_power_availability")
private String dcPowerAvailability;
@JsonProperty("customer_dcdb_mcb_availability")
private String customerDcdbMcbAvailability;
@JsonProperty("customer_dcdb_mcb_rating")
private String customerDcdbMcbRating;
@JsonProperty("rack_dcdb_mcb_availability")
private String rackDcdbMcbAvailability;
@JsonProperty("rack_dcdb_mcb_rating")
private String rackDcdbMcbRating;
@JsonProperty("proposed_rack_to_dcdb_distance")
private String proposedRackToDcdbDistance;
@JsonProperty("ground_bar_available")
private String groundBarAvailable;
@JsonProperty("proposed_rack_to_ground_bar_distance")
private String proposedRackToGroundBarDistance;
@JsonProperty("termination_1_type")
private String termination1Type;
@JsonProperty("termination_1_distance")
private String termination1Distance;
@JsonProperty("termination_2_type")
private String termination2Type;
@JsonProperty("termination_2_distance")
private String termination2Distance;
@JsonProperty("termination_3_type")
private String termination3Type;
@JsonProperty("termination_3_distance")
private String termination3Distance;
@JsonProperty("termination_4_type")
private String termination4Type;
@JsonProperty("termination_4_distance")
private String termination4Distance;
@JsonProperty("migration_required")
private String migrationRequired;
@JsonProperty("site_engineer_name")
private String siteEngineerName;
@JsonProperty("site_engineer_number")
private String siteEngineerNumber;
@JsonProperty("site_engineer_email")
private String siteEngineerEmail;
@JsonProperty("site_in_charge_name")
private String siteInChargeName;
@JsonProperty("site_in_charge_number")
private String siteInChargeNumber;
@JsonProperty("site_in_charge_email")
private String siteInChargeEmail;
@JsonProperty("site_manager_name")
private String siteManagerName;
@JsonProperty("site_manager_number")
private String siteManagerNumber;
@JsonProperty("site_manager_email")
private String siteManagerEmail;
@JsonProperty("escalation_manager_name")
private String escalationManagerName;
@JsonProperty("escalation_manager_number")
private String escalationManagerNumber;
@JsonProperty("escalation_manager_email")
private String escalationManagerEmail;
@JsonProperty("status")
private String status;
@JsonProperty("site_status")
private String siteStatus;
@JsonProperty("created_by")
private String createdBy;
@JsonProperty("created_at")
private String createdAt;
@JsonProperty("timestamp")
private String timestamp;
@JsonProperty("delete_flag")
private String deleteFlag;
@JsonProperty("date")
private String date;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("site_id")
public String getSiteId() {
return siteId;
}

@JsonProperty("site_id")
public void setSiteId(String siteId) {
this.siteId = siteId;
}

@JsonProperty("project_id")
public String getProjectId() {
return projectId;
}

@JsonProperty("project_id")
public void setProjectId(String projectId) {
this.projectId = projectId;
}

@JsonProperty("circle_id")
public String getCircleId() {
return circleId;
}

@JsonProperty("circle_id")
public void setCircleId(String circleId) {
this.circleId = circleId;
}

@JsonProperty("district_id")
public String getDistrictId() {
return districtId;
}

@JsonProperty("district_id")
public void setDistrictId(String districtId) {
this.districtId = districtId;
}

@JsonProperty("notification_group_id")
public String getNotificationGroupId() {
return notificationGroupId;
}

@JsonProperty("notification_group_id")
public void setNotificationGroupId(String notificationGroupId) {
this.notificationGroupId = notificationGroupId;
}

@JsonProperty("site_name")
public String getSiteName() {
return siteName;
}

@JsonProperty("site_name")
public void setSiteName(String siteName) {
this.siteName = siteName;
}

@JsonProperty("site_address")
public String getSiteAddress() {
return siteAddress;
}

@JsonProperty("site_address")
public void setSiteAddress(String siteAddress) {
this.siteAddress = siteAddress;
}

@JsonProperty("pin_code")
public String getPinCode() {
return pinCode;
}

@JsonProperty("pin_code")
public void setPinCode(String pinCode) {
this.pinCode = pinCode;
}

@JsonProperty("site_latitude")
public String getSiteLatitude() {
return siteLatitude;
}

@JsonProperty("site_latitude")
public void setSiteLatitude(String siteLatitude) {
this.siteLatitude = siteLatitude;
}

@JsonProperty("site_longitude")
public String getSiteLongitude() {
return siteLongitude;
}

@JsonProperty("site_longitude")
public void setSiteLongitude(String siteLongitude) {
this.siteLongitude = siteLongitude;
}

@JsonProperty("country")
public String getCountry() {
return country;
}

@JsonProperty("country")
public void setCountry(String country) {
this.country = country;
}

@JsonProperty("state")
public String getState() {
return state;
}

@JsonProperty("state")
public void setState(String state) {
this.state = state;
}

@JsonProperty("district")
public String getDistrict() {
return district;
}

@JsonProperty("district")
public void setDistrict(String district) {
this.district = district;
}

@JsonProperty("city")
public String getCity() {
return city;
}

@JsonProperty("city")
public void setCity(String city) {
this.city = city;
}

@JsonProperty("zone")
public String getZone() {
return zone;
}

@JsonProperty("zone")
public void setZone(String zone) {
this.zone = zone;
}

@JsonProperty("hub_code")
public String getHubCode() {
return hubCode;
}

@JsonProperty("hub_code")
public void setHubCode(String hubCode) {
this.hubCode = hubCode;
}

@JsonProperty("proposed_equipment")
public String getProposedEquipment() {
return proposedEquipment;
}

@JsonProperty("proposed_equipment")
public void setProposedEquipment(String proposedEquipment) {
this.proposedEquipment = proposedEquipment;
}

@JsonProperty("shelter_size")
public String getShelterSize() {
return shelterSize;
}

@JsonProperty("shelter_size")
public void setShelterSize(String shelterSize) {
this.shelterSize = shelterSize;
}

@JsonProperty("rack_available")
public String getRackAvailable() {
return rackAvailable;
}

@JsonProperty("rack_available")
public void setRackAvailable(String rackAvailable) {
this.rackAvailable = rackAvailable;
}

@JsonProperty("available_rack_size")
public String getAvailableRackSize() {
return availableRackSize;
}

@JsonProperty("available_rack_size")
public void setAvailableRackSize(String availableRackSize) {
this.availableRackSize = availableRackSize;
}

@JsonProperty("required_rack_size")
public String getRequiredRackSize() {
return requiredRackSize;
}

@JsonProperty("required_rack_size")
public void setRequiredRackSize(String requiredRackSize) {
this.requiredRackSize = requiredRackSize;
}

@JsonProperty("installation_start_u_position_bottom")
public String getInstallationStartUPositionBottom() {
return installationStartUPositionBottom;
}

@JsonProperty("installation_start_u_position_bottom")
public void setInstallationStartUPositionBottom(String installationStartUPositionBottom) {
this.installationStartUPositionBottom = installationStartUPositionBottom;
}

@JsonProperty("manned_site")
public String getMannedSite() {
return mannedSite;
}

@JsonProperty("manned_site")
public void setMannedSite(String mannedSite) {
this.mannedSite = mannedSite;
}

@JsonProperty("key_location")
public String getKeyLocation() {
return keyLocation;
}

@JsonProperty("key_location")
public void setKeyLocation(String keyLocation) {
this.keyLocation = keyLocation;
}

@JsonProperty("key_incharge_name")
public String getKeyInchargeName() {
return keyInchargeName;
}

@JsonProperty("key_incharge_name")
public void setKeyInchargeName(String keyInchargeName) {
this.keyInchargeName = keyInchargeName;
}

@JsonProperty("key_incharge_number")
public String getKeyInchargeNumber() {
return keyInchargeNumber;
}

@JsonProperty("key_incharge_number")
public void setKeyInchargeNumber(String keyInchargeNumber) {
this.keyInchargeNumber = keyInchargeNumber;
}

@JsonProperty("ring_or_linear")
public String getRingOrLinear() {
return ringOrLinear;
}

@JsonProperty("ring_or_linear")
public void setRingOrLinear(String ringOrLinear) {
this.ringOrLinear = ringOrLinear;
}

@JsonProperty("media_availability")
public String getMediaAvailability() {
return mediaAvailability;
}

@JsonProperty("media_availability")
public void setMediaAvailability(String mediaAvailability) {
this.mediaAvailability = mediaAvailability;
}

@JsonProperty("dc_power_availability")
public String getDcPowerAvailability() {
return dcPowerAvailability;
}

@JsonProperty("dc_power_availability")
public void setDcPowerAvailability(String dcPowerAvailability) {
this.dcPowerAvailability = dcPowerAvailability;
}

@JsonProperty("customer_dcdb_mcb_availability")
public String getCustomerDcdbMcbAvailability() {
return customerDcdbMcbAvailability;
}

@JsonProperty("customer_dcdb_mcb_availability")
public void setCustomerDcdbMcbAvailability(String customerDcdbMcbAvailability) {
this.customerDcdbMcbAvailability = customerDcdbMcbAvailability;
}

@JsonProperty("customer_dcdb_mcb_rating")
public String getCustomerDcdbMcbRating() {
return customerDcdbMcbRating;
}

@JsonProperty("customer_dcdb_mcb_rating")
public void setCustomerDcdbMcbRating(String customerDcdbMcbRating) {
this.customerDcdbMcbRating = customerDcdbMcbRating;
}

@JsonProperty("rack_dcdb_mcb_availability")
public String getRackDcdbMcbAvailability() {
return rackDcdbMcbAvailability;
}

@JsonProperty("rack_dcdb_mcb_availability")
public void setRackDcdbMcbAvailability(String rackDcdbMcbAvailability) {
this.rackDcdbMcbAvailability = rackDcdbMcbAvailability;
}

@JsonProperty("rack_dcdb_mcb_rating")
public String getRackDcdbMcbRating() {
return rackDcdbMcbRating;
}

@JsonProperty("rack_dcdb_mcb_rating")
public void setRackDcdbMcbRating(String rackDcdbMcbRating) {
this.rackDcdbMcbRating = rackDcdbMcbRating;
}

@JsonProperty("proposed_rack_to_dcdb_distance")
public String getProposedRackToDcdbDistance() {
return proposedRackToDcdbDistance;
}

@JsonProperty("proposed_rack_to_dcdb_distance")
public void setProposedRackToDcdbDistance(String proposedRackToDcdbDistance) {
this.proposedRackToDcdbDistance = proposedRackToDcdbDistance;
}

@JsonProperty("ground_bar_available")
public String getGroundBarAvailable() {
return groundBarAvailable;
}

@JsonProperty("ground_bar_available")
public void setGroundBarAvailable(String groundBarAvailable) {
this.groundBarAvailable = groundBarAvailable;
}

@JsonProperty("proposed_rack_to_ground_bar_distance")
public String getProposedRackToGroundBarDistance() {
return proposedRackToGroundBarDistance;
}

@JsonProperty("proposed_rack_to_ground_bar_distance")
public void setProposedRackToGroundBarDistance(String proposedRackToGroundBarDistance) {
this.proposedRackToGroundBarDistance = proposedRackToGroundBarDistance;
}

@JsonProperty("termination_1_type")
public String getTermination1Type() {
return termination1Type;
}

@JsonProperty("termination_1_type")
public void setTermination1Type(String termination1Type) {
this.termination1Type = termination1Type;
}

@JsonProperty("termination_1_distance")
public String getTermination1Distance() {
return termination1Distance;
}

@JsonProperty("termination_1_distance")
public void setTermination1Distance(String termination1Distance) {
this.termination1Distance = termination1Distance;
}

@JsonProperty("termination_2_type")
public String getTermination2Type() {
return termination2Type;
}

@JsonProperty("termination_2_type")
public void setTermination2Type(String termination2Type) {
this.termination2Type = termination2Type;
}

@JsonProperty("termination_2_distance")
public String getTermination2Distance() {
return termination2Distance;
}

@JsonProperty("termination_2_distance")
public void setTermination2Distance(String termination2Distance) {
this.termination2Distance = termination2Distance;
}

@JsonProperty("termination_3_type")
public String getTermination3Type() {
return termination3Type;
}

@JsonProperty("termination_3_type")
public void setTermination3Type(String termination3Type) {
this.termination3Type = termination3Type;
}

@JsonProperty("termination_3_distance")
public String getTermination3Distance() {
return termination3Distance;
}

@JsonProperty("termination_3_distance")
public void setTermination3Distance(String termination3Distance) {
this.termination3Distance = termination3Distance;
}

@JsonProperty("termination_4_type")
public String getTermination4Type() {
return termination4Type;
}

@JsonProperty("termination_4_type")
public void setTermination4Type(String termination4Type) {
this.termination4Type = termination4Type;
}

@JsonProperty("termination_4_distance")
public String getTermination4Distance() {
return termination4Distance;
}

@JsonProperty("termination_4_distance")
public void setTermination4Distance(String termination4Distance) {
this.termination4Distance = termination4Distance;
}

@JsonProperty("migration_required")
public String getMigrationRequired() {
return migrationRequired;
}

@JsonProperty("migration_required")
public void setMigrationRequired(String migrationRequired) {
this.migrationRequired = migrationRequired;
}

@JsonProperty("site_engineer_name")
public String getSiteEngineerName() {
return siteEngineerName;
}

@JsonProperty("site_engineer_name")
public void setSiteEngineerName(String siteEngineerName) {
this.siteEngineerName = siteEngineerName;
}

@JsonProperty("site_engineer_number")
public String getSiteEngineerNumber() {
return siteEngineerNumber;
}

@JsonProperty("site_engineer_number")
public void setSiteEngineerNumber(String siteEngineerNumber) {
this.siteEngineerNumber = siteEngineerNumber;
}

@JsonProperty("site_engineer_email")
public String getSiteEngineerEmail() {
return siteEngineerEmail;
}

@JsonProperty("site_engineer_email")
public void setSiteEngineerEmail(String siteEngineerEmail) {
this.siteEngineerEmail = siteEngineerEmail;
}

@JsonProperty("site_in_charge_name")
public String getSiteInChargeName() {
return siteInChargeName;
}

@JsonProperty("site_in_charge_name")
public void setSiteInChargeName(String siteInChargeName) {
this.siteInChargeName = siteInChargeName;
}

@JsonProperty("site_in_charge_number")
public String getSiteInChargeNumber() {
return siteInChargeNumber;
}

@JsonProperty("site_in_charge_number")
public void setSiteInChargeNumber(String siteInChargeNumber) {
this.siteInChargeNumber = siteInChargeNumber;
}

@JsonProperty("site_in_charge_email")
public String getSiteInChargeEmail() {
return siteInChargeEmail;
}

@JsonProperty("site_in_charge_email")
public void setSiteInChargeEmail(String siteInChargeEmail) {
this.siteInChargeEmail = siteInChargeEmail;
}

@JsonProperty("site_manager_name")
public String getSiteManagerName() {
return siteManagerName;
}

@JsonProperty("site_manager_name")
public void setSiteManagerName(String siteManagerName) {
this.siteManagerName = siteManagerName;
}

@JsonProperty("site_manager_number")
public String getSiteManagerNumber() {
return siteManagerNumber;
}

@JsonProperty("site_manager_number")
public void setSiteManagerNumber(String siteManagerNumber) {
this.siteManagerNumber = siteManagerNumber;
}

@JsonProperty("site_manager_email")
public String getSiteManagerEmail() {
return siteManagerEmail;
}

@JsonProperty("site_manager_email")
public void setSiteManagerEmail(String siteManagerEmail) {
this.siteManagerEmail = siteManagerEmail;
}

@JsonProperty("escalation_manager_name")
public String getEscalationManagerName() {
return escalationManagerName;
}

@JsonProperty("escalation_manager_name")
public void setEscalationManagerName(String escalationManagerName) {
this.escalationManagerName = escalationManagerName;
}

@JsonProperty("escalation_manager_number")
public String getEscalationManagerNumber() {
return escalationManagerNumber;
}

@JsonProperty("escalation_manager_number")
public void setEscalationManagerNumber(String escalationManagerNumber) {
this.escalationManagerNumber = escalationManagerNumber;
}

@JsonProperty("escalation_manager_email")
public String getEscalationManagerEmail() {
return escalationManagerEmail;
}

@JsonProperty("escalation_manager_email")
public void setEscalationManagerEmail(String escalationManagerEmail) {
this.escalationManagerEmail = escalationManagerEmail;
}

@JsonProperty("status")
public String getStatus() {
return status;
}

@JsonProperty("status")
public void setStatus(String status) {
this.status = status;
}

@JsonProperty("site_status")
public String getSiteStatus() {
return siteStatus;
}

@JsonProperty("site_status")
public void setSiteStatus(String siteStatus) {
this.siteStatus = siteStatus;
}

@JsonProperty("created_by")
public String getCreatedBy() {
return createdBy;
}

@JsonProperty("created_by")
public void setCreatedBy(String createdBy) {
this.createdBy = createdBy;
}

@JsonProperty("created_at")
public String getCreatedAt() {
return createdAt;
}

@JsonProperty("created_at")
public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

@JsonProperty("timestamp")
public String getTimestamp() {
return timestamp;
}

@JsonProperty("timestamp")
public void setTimestamp(String timestamp) {
this.timestamp = timestamp;
}

@JsonProperty("delete_flag")
public String getDeleteFlag() {
return deleteFlag;
}

@JsonProperty("delete_flag")
public void setDeleteFlag(String deleteFlag) {
this.deleteFlag = deleteFlag;
}

@JsonProperty("date")
public String getDate() {
return date;
}

@JsonProperty("date")
public void setDate(String date) {
this.date = date;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
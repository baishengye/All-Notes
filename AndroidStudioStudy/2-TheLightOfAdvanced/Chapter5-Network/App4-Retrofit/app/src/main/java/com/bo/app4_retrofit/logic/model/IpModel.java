package com.bo.app4_retrofit.logic.model;

public class IpModel {
    private int code;
    private IpData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public IpData getData() {
        return data;
    }

    public void setData(IpData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IpModel{");
        sb.append("code=").append(code);
        sb.append(", data=").append(data.toString());
        sb.append('}');
        return sb.toString();
    }

    class IpData {
        private String country;
        private String country_id;
        private String area;
        private String area_id;
        private String region;
        private String region_id;
        private String city;
        private String city_id;
        private String county;
        private String county_id;
        private String isp;
        private String isp_id;
        private String ip;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getCounty_id() {
            return county_id;
        }

        public void setCounty_id(String county_id) {
            this.county_id = county_id;
        }

        public String getIsp() {
            return isp;
        }

        public void setIsp(String isp) {
            this.isp = isp;
        }

        public String getIsp_id() {
            return isp_id;
        }

        public void setIsp_id(String isp_id) {
            this.isp_id = isp_id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("IpData{");
            sb.append("country='").append(country).append('\'');
            sb.append(", country_id='").append(country_id).append('\'');
            sb.append(", area='").append(area).append('\'');
            sb.append(", area_id='").append(area_id).append('\'');
            sb.append(", region='").append(region).append('\'');
            sb.append(", region_id='").append(region_id).append('\'');
            sb.append(", city='").append(city).append('\'');
            sb.append(", city_id='").append(city_id).append('\'');
            sb.append(", county='").append(county).append('\'');
            sb.append(", county_id='").append(county_id).append('\'');
            sb.append(", isp='").append(isp).append('\'');
            sb.append(", isp_id='").append(isp_id).append('\'');
            sb.append(", ip='").append(ip).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

}

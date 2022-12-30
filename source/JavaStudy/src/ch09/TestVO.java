package ch09;

/**
 * 테스트 VO
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class TestVO implements Cloneable {
    private int ssdSize = 0;
    private int ramSize = 0;
    private String cpuNm = "";
    
    public int getSsdSize() {
        return ssdSize;
    }
    public void setSsdSize(int ssdSize) {
        this.ssdSize = ssdSize;
    }
    public int getRamSize() {
        return ramSize;
    }
    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }
    public String getCpuNm() {
        return cpuNm;
    }
    public void setCpuNm(String cpuNm) {
        this.cpuNm = cpuNm;
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("item.hashCode : ");
        sb.append(this.hashCode());
        sb.append(" / ");
        sb.append("item : ");
        sb.append("[");
        sb.append("cpuNm : ");
        sb.append(this.getCpuNm());
        sb.append(", ");
        sb.append("ssdSize :");
        sb.append(this.getSsdSize());
        sb.append(", ");
        sb.append("ramSize : ");
        sb.append(this.getRamSize());
        sb.append("]");
        
        return sb.toString();
    }
}

package net.namlongadv.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author ToanNgo
 *
 * @param <T>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDTO<T> {
	/**
	 * Page count
	 */
    private long count;
    
    /**
     * Page data
     */
    private List<T> data;

    public PageDTO() {
    }

    /**
     * Create PageDTO instance with all arguments
     * 
     * @param count
     * @param data
     */
    public PageDTO(long count, List<T> data) {
        this.count = count;
        this.data = data;
    }

    /**
     * Get count
     *
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * Set count
     *
     * @param count
     */
    public void setCount(long count) {
        this.count = count;
    }

    /**
     * Get data
     *
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Set data
     *
     * @param data
     */
    public void setData(List<T> data) {
        this.data = data;
    }

}

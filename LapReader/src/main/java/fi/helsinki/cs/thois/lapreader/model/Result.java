package fi.helsinki.cs.thois.lapreader.model;

/**
 *
 * Database model for easier handling of result of a heat.
 */
class Result extends Model {
    /**
     * Runtime in thousands of seconds
     */
    int time;
    /**
     * Full laps run in a heat
     */
    int laps;
    
    Result() {
        
    }
    
    Result(int laps, int time) {
        this.laps = laps;
        this.time = time;
    }
    
    
    /**
     * Getting full minutes for easier formatting
     * @return full minutes
     */
    public int minutes() {
        return time/60000;
    }
    
    /**
     * Getting full seconds-full minutes for easier formatting.
     * @return full seconds
     */
    public int seconds() {
        return (time%60000)/1000;
    }
    /**
     * Getting mixroseconds-full seconds for easier formatting
     * @return microseconds
     */
    public int microseconds() {
        return time%1000;
    }
    
    @Override
    public String toString() {
        return laps + " laps " + minutes() + ":" + seconds() + "." + microseconds();
    }
    
}

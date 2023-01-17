/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	William Zhang
 *	@since	01/09/2023
 */
import java.util.List;
public class City implements Comparable<City> {

    // fields
    private String cityName;
    private String stateName    ;
    private String cityType;
    private int population;

    // constructor
    public City(String state, String name, String type, int pop) {
        cityName = name;
        stateName = state;
        cityType = type;
        population = pop;
    }

    /**	Compare two cities populations
     *	@param other		the other City to compare
     *	@return				the following value:
     *		If populations are different, then returns (this.population - other.population)
     *		else if states are different, then returns (this.state - other.state)
     *		else returns (this.name - other.name)
     */
    @Override
    public int compareTo(City other){
        if(this.getPopulation() != other.getPopulation()){
            return this.population	- other.getPopulation();
        } else if(!this.getStateName().equals(other.getStateName())){
            return this.getStateName().compareTo(other.getStateName());
        } else {
            return this.getCityName().compareTo(other.getCityName());
        }
    }

    /**	An enhanced compareTo()
     *	@param other		the other City to compare
     *  @param mode         the type of comparison - 1 for state name, 0 for population
     *	@return				the following value:
     *		If populations are different, then returns (this.population - other.population)
     *		else if states are different, then returns (this.state - other.state)
     *		else returns (this.name - other.name)
     */
    public int compareTo(City other, int mode){
        if(mode == 0){
            if(this.getPopulation() != other.getPopulation()){
                return this.population	- other.getPopulation();
            } else if(!this.getStateName().equals(other.getStateName())){
                return this.getStateName().compareTo(other.getStateName());
            } else {
                return this.getCityName().compareTo(other.getCityName());
            }
        } else if(mode == 1) {
            if(!this.getStateName().equals(other.getStateName())){
                return this.getStateName().compareTo(other.getStateName());
            } else if(this.getPopulation() != other.getPopulation()){
                return this.population	- other.getPopulation();
            } else {
                return this.getCityName().compareTo(other.getCityName());
            }
        } else {
            if(!this.getCityName().equals(other.getCityName())){
                return this.getCityName().compareTo(other.getCityName());
            } else if(!this.getStateName().equals(other.getStateName())){
                return this.getStateName().compareTo(other.getStateName());
            } else {
                return this.population	- other.getPopulation();
            }
        }
    }

    /**	Equal city name and state name
     *	@param other		the other City to compare
     *	@return				true if city name and state name equal; false otherwise
     */
    public boolean equals(City other){
        return cityName.equals(other.getCityName()) && stateName.equals(other.getStateName());
    }


    /**	Accessor methods */
    public int getPopulation(){
        return population;
    }
    public String getCityName(){
        return cityName;
    }
    public String getStateName(){
        return stateName;
    }
    public String getCityType(){
        return cityType;
    }

    /**	toString */
    @Override
    public String toString() {
        return String.format("%-22s %-25s %-12s %,12d", stateName, cityName, cityType, population);
    }
}

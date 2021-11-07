
package Pack;

public class City {
    //Attributes
    private Human human;
    private House house;
    private int population = 12345 ;

    //Constructor
    public City(Human human, House house){
        this.human = human;
        this.house = house;
    }

    //Getters and Setters
    public Human getHuman() {
        return human;
    }

    public House getHouse() {
        return house;
    }

    public int getPopulation()
    {
        return population;
    }


    //Equals
    @Override
    public boolean equals(Object obj) {
        if (obj == this ) return  true;

        if (obj instanceof City){
            City city = (City)obj;
            if(city.getHuman().equals(this.getHuman()) && city.getHouse().equals(this.getHouse())) return true;
        }
        return  false;
    }

    //HashCode
    @Override
    public int hashCode(){
        return human.hashCode() + house.hashCode();
    }

    //The toString() describes itself
    
    @Override
    public String toString(){
        return human + " " + house;
    }


}

import java.util.List;
import java.util.ArrayList;
/**
 *	Population - Sorts a database of over 30,000 US Cities by population,
 *  city name, or state name. Also can request only a certain state's cities
 *  to be sorted or a certain city name to be sorted.
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author	William Zhang
 *	@since	01/09/2023
 */
import java.util.Scanner;
public class Population {

    // List of cities
    private List<City> cities = new ArrayList <City>();
    private List<City> citiesByState = new ArrayList <City>();
    private List<City> citiesByCity = new ArrayList <City>();

    // US data file
    private final String DATA_FILE = "usPopData2017.txt";

    // etc.
    private boolean quitted;
    Scanner scan;

    //constructor
    public Population(){
        scan = FileUtils.openToRead(DATA_FILE);
        quitted = false;
    }

    public static void main(String[]args){
        Population pop = new Population();
        pop.run();
    }

    /** sets up the ArrayList with City objects by reading
      * the txt file
    */
    public void loadData(){
        while(scan.hasNext()){
            String scanned = scan.next(); // first word of state name
            String state = "";
            String city = "";
            String cityType = "";
            int population = 0;
            if(scanned.equals("New") || scanned.equals("North") || scanned.equals("South")
                    || scanned.equals("West") || scanned.equals("Rhode")){
                state = scanned + " " + scan.next();
            } else if(scanned.equals("District")){
                state = scanned +  " " + scan.next() + " " + scan.next();
            } else {
                state = scanned;
            }
            scanned = scan.next(); // first word of city name
            while(!scanned.equals("city") && !scanned.equals("town") && !scanned.equals("township")
                    && !scanned.equals("municipality") && !scanned.equals("village") &&
                    !scanned.equals("government")){
                city = city + scanned + " ";
                scanned = scan.next(); // next word of state name or beginning of city type
            }
            cityType = scanned;
            scanned = scan.next();
            population = Integer.parseInt(scanned);
            City c = new City(state, city, cityType, population);
            cities.add(c);
        }
    }


    public void run(){
        loadData();
        printIntroduction();
        while(!quitted){
            printMenu();
            int selection = Prompt.getInt("Enter Selection");
            switch(selection){
                case 1:
                    //do selection sort for least populous cities
                    System.out.println("\nFifty least populous cities");
                    selectionSort(cities);
                    break;
                case 2:
                    //do merge sort for most populous cities
                    System.out.println("\nFifty most populous cities");
                    mergeSort(cities, 0);
                    break;
                case 3:
                    //do insertion sort by city name
                    System.out.println("\nFifty cities sorted by name");
                    insertionSort(cities);
                    break;
                case 4:
                    //do merge sort by reverse city name
                    System.out.println("\nFifty cities sorted by name descending");
                    mergeSort(cities, 2);
                    break;
                case 5:
                    //do case 2 but for a specific state
                    citiesByState.clear();
                    String state = Prompt.getString("Enter state name (ie. Ohio)");
                    int counter1 = 0;
                    boolean accepted1 = false;
                    while(!accepted1){
                        for(int i = 0; i < cities.size(); i++){
                            City c = cities.get(i);
                            if(c.getStateName().equalsIgnoreCase(state)){
                                citiesByState.add(c);
                                counter1++;
                            }
                        }
                        if(counter1 == 0){
                            System.out.println("ERROR: " + state + " is not valid");
                            state = Prompt.getString("Enter state name (ie. Ohio)");
                        } else accepted1 = true;
                    }
                    System.out.println("\nFifty most populous cities in " + state);
                    mergeSort(citiesByState, 0);
                    break;
                case 6:
                    //do case 2 but for a specific city name
                    citiesByCity.clear();
                    String city = Prompt.getString("Enter city name");
                    int counter2 = 0;
                    boolean accepted2 = false;
                    while(!accepted2){
                        for(int i = 0; i < cities.size(); i++){
                            City c = cities.get(i);
                            if(c.getCityName().trim().equals(city)) {
                                citiesByCity.add(c);
                                counter2++;
                            }
                        }
                        if(counter2 == 0){
                            System.out.println("ERROR: " + city + " is not valid");
                            city = Prompt.getString("Enter city name");
                        } else accepted2 = true;
                    }
                    System.out.println("\nCity " + city + " by population");
                    mergeSort(citiesByCity, 0);
                    break;
                case 9:
                    quitted = true;
            }
        }
        System.out.println("\nThanks for using Population!");
    }

    /**	Prints the introduction to Population */
    public void printIntroduction() {
        System.out.println("   ___                  _       _   _");
        System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
        System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
        System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
        System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
        System.out.println("           |_|");
        System.out.println("\n31765 cities in database\n");
    }

    /**	Print out the choices for population sorting */
    public void printMenu() {
        System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
        System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
        System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
        System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
        System.out.println("5. Fifty most populous cities in named state");
        System.out.println("6. All cities matching a name sorted by population");
        System.out.println("9. Quit");
    }

    /**
     *	Swaps two City objects in list arr
     *	@param c		list of City objects
     *	@param x		index of first object to swap
     *	@param y		index of second object to swap
     */
    private void swap (List<City> c, int x, int y) {
        City temp1 = c.get(x);
        City temp2 = c.get(y);
        c.set(x, temp2);
        c.set(y, temp1);
    }

    /**
     *	Selection Sort algorithm to sort cities by descending population
     *	@param c		list of City objects to sort
     */
    public void selectionSort(List<City> c) {
        long startMillisec = System.currentTimeMillis();
        for(int a = c.size()-1; a > 0; a--) {
            int maxIndex = a;
            for (int i = 0; i < a; i++) {
                if (c.get(i).compareTo(c.get(maxIndex), 0) >= 0) {
                    maxIndex = i;
                }
            }
            swap(c, maxIndex, a);
        }
        long endMillisec = System.currentTimeMillis();
        printList(c);
        System.out.println("\nElapsed Time: " + (endMillisec - startMillisec)
                + " milliseconds\n");
    }

    /**
     *	Insertion Sort algorithm to sort city names in order
     *	@param c		list of City objects to sort
     */
    public void insertionSort(List<City> c) {
        long startMillisec = System.currentTimeMillis();
        // by city name
        for(int a = 1; a < c.size(); a++){
            City c1 = c.get(a);
            int i = a;
            while(i > 0 && c1.getCityName().compareTo(c.get(i-1).getCityName()) <= 0){
                c.set(i, c.get(i-1));
                i--;
            }
            c.set(i, c1);
        }
        long endMillisec = System.currentTimeMillis();
        printList(c);
        System.out.println("\nElapsed Time: " + (endMillisec - startMillisec)
                + " milliseconds\n");
    }

    /**
     *	Merge Sort algorithm to sort cities according to sortType
     *	@param c		list of City objects to sort
     * 	@param sortType method of sorting - 0 sorts by population, 1 sorts by city name
     */
    public void mergeSort(List<City> c, int sortType) {
        long startMillisec = System.currentTimeMillis();
        recurseSort(c, 0, c.size()-1, sortType);
        long endMillisec = System.currentTimeMillis();
        reverseList(c);
        printList(c);
        System.out.println("\nElapsed Time: " + (endMillisec - startMillisec)
                + " milliseconds\n");
    }

    /**
     *	Helper recursive method for mergeSort
     *	@param c		list of City objects to sort
     * 	@param from     The first element index of the recursed section
     *  @param to       The last element of the recursed section
     *  @param sortType The method of sorting - identical to mergeSort
     */
    public void recurseSort(List<City> c, int from, int to, int sortType){
        if(to - from < 2){
            if(to > from && c.get(to).compareTo(c.get(from), sortType) < 0){
                swap(c, to, from);
            }
        } else {
            int middle = (from + to) / 2;
            recurseSort(c, from, middle, sortType);
            recurseSort(c, middle+1, to, sortType);
            merge(c, from, middle, to, sortType);
        }
    }

    /**
     *	Helper method for recurseSort - combines two sections of an arraylist together
     *	@param c		list of City objects to sort
     * 	@param from     The first element index of one section
     *  @param middle   The last element of one section
     *  @param to       The last element of the other section
     *  @param sortType The method of sorting - identical to mergeSort
     */
    public void merge(List<City> c, int from, int middle, int to, int sortType){
        City[] temp = new City[c.size()];
        int i = from;
        int j = middle+1;
        int k = from;
        while(i <= middle && j <= to){
            if(c.get(i).compareTo(c.get(j), sortType) < 0){
                temp[k] = c.get(i);
                i++;
            } else {
                temp[k] = c.get(j);
                j++;
            }
            k++;
        }
        while (i <= middle) {
            temp[k] = c.get(i);
            i++; k++;
        }
        while(j <= to){
            temp[k] = c.get(j);
            j++; k++;
        }
        for(k = from; k <= to; k++){
            c.set(k, temp[k]);
        }
    }

    /**
     *	Reverse List algorithm - takes a sorted list and reverses the order
     *	@param c		sorted list of City objects to reverse
     */
    public void reverseList(List<City> c){
        for(int i = 0; i < c.size(); i++){
            City city = c.get(i);
            c.remove(i);
            c.add(0, city);
        }
    }

    /**
     *	prints the sorted list of cities
     *	@param cityList		list of City objects to print
     */
    public void printList(List<City> cityList){
        System.out.printf("    %-22s %-25s %-12s %12s \n", "State", "City", "Type", "Population");
        for(int i = 0; i < cityList.size(); i++){
            City c = cityList.get(i);
            if(i < 9) System.out.println(" " + (i+1) + ": " + c);
            else System.out.println(i+1 + ": " + c);
            if(i == 49) i = cityList.size();
        }
    }
}



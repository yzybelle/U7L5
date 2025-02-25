import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> allCast;
    private ArrayList<String> allGenres;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        allCast = new ArrayList<String>();
        allGenres = new ArrayList<String>();
        getAllCast();
        getAllGenres();
    }

    public void getAllCast(){
        for (int i = 0; i < movies.size(); i++) {
            String temp = movies.get(i).getCast();
            String[] cast = temp.split("\\|");
            for (int j = 0; j < cast.length; j++) {
                if (!(allCast.contains(cast[j]))){
                    allCast.add(cast[j]);
                }
            }
        }
    }
    public void getAllGenres(){
        for (int i = 0; i < movies.size(); i++) {
            String temp = movies.get(i).getGenres();
            String[] genres = temp.split("\\|");
            for (int j = 0; j < genres.length; j++) {
                if (!(allGenres.contains(genres[j]))){
                    allGenres.add(genres[j]);
                }
            }
        }
    }
    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }
    private void sortAlpha(ArrayList<String> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String tempTitle = listToSort.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1)) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, tempTitle);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Cast: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < allCast.size(); i++) {
            if(allCast.get(i).toLowerCase().contains(searchTerm)){
                names.add(allCast.get(i));
            }
        }
        sortAlpha(names);

        for (int i = 0; i < names.size(); i++)
        {
            String name = names.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + name);
        }

        System.out.println("Which actor would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        searchTerm = names.get(choice-1);

ArrayList<String> appear = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++) {
            if(movies.get(i).getCast().contains(searchTerm)){
                appear.add(movies.get(i).getTitle());
            }
        }
        sortAlpha(appear);
        for (int i = 0; i < appear.size(); i++)
        {
            String name = appear.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + name);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        searchTerm = appear.get(choice-1);

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getTitle()== searchTerm){
                displayMovieInfo(movies.get(i));
            }

        }
    }

    private void searchKeywords()
    {
        System.out.print("Keyword: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<String> movie = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++) {
            if(movies.get(i).getKeywords().contains(searchTerm)){
            movie.add(movies.get(i).getTitle());
            }
        }
        System.out.println(movie);

    }

    private void listGenres()
    {
     sortAlpha(allGenres);
        System.out.println("Here are all the genres:");
        for (int i = 0; i < allGenres.size(); i++)
        {
            String name = allGenres.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + name);
        }
        System.out.println("Which genre would you like to know which movies have? (Enter a number)");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String genre = allGenres.get(choice-1);

        ArrayList<String> gmovies = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++) {
            if(movies.get(i).getGenres().toLowerCase().contains(genre) ){
                gmovies.add()
            }
        }

    }

    private void listHighestRated()
    {

    }

    private void listHighestRevenue()
    {

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
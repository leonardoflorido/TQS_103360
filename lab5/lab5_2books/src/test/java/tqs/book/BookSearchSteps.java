package tqs.book;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookSearchSteps {
    Library library = new Library();
    List<Book> result = new ArrayList<>();

    @ParameterType(".*")
    public Date date(String date) {
        if (date.length() <= 4) {
            return new Date(Integer.parseInt(date) - 1900, Calendar.JANUARY, 1);
        }
        return new Date(date);
    }

    @Given("a book with the title {string}, written by {string}, published in {date}")
    public void addNewBook(final String title, final String author, final Date published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @Given("another book with the title {string}, written by {string}, published in {date}")
    public void addAnotherBook(final String title, final String author, final Date published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @When("the customer searches for books published between {date} and {date}")
    public void setSearchParameters(final Date from, final Date to) {
        result = library.findBooks(from, to);
    }

    @Then("{int} books should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @Then("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        assertThat(result.get(position - 1).getTitle(), equalTo(title));
    }
}

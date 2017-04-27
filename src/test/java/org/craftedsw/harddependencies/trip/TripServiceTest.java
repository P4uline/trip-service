package org.craftedsw.harddependencies.trip;

import org.craftedsw.harddependencies.exception.UserNotLoggedInException;
import org.craftedsw.harddependencies.user.User;
import org.craftedsw.harddependencies.user.UserSession;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by pauline on 09/03/17.
 */
public class TripServiceTest {

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_when_not_logged() throws Exception {
        final User loggedUser = null;

        final UserSession testSession = getUserSession(loggedUser);
        final TripService tripService = getTripService(testSession);

        tripService.getTripsByUser(new User());
    }

    @Test
    public void should_return8empty_list_when_user_is_not_my_friend() throws Exception {

        final User loggedUser = new User();

        final UserSession testSession = getUserSession(loggedUser);
        final TripService tripService = getTripService(testSession);

        List<Trip> trips = tripService.getTripsByUser(new User());
        assertThat(trips).isEmpty();
    }

    @Test
    public void should_call_dao_when_user_is_a_friend() throws Exception {
        User loggedUser = new User();
        User friendUser = new User();
        User otherGuy = new User();
        friendUser.addFriend(loggedUser);
        friendUser.addFriend(otherGuy);

        final UserSession testSession = getUserSession(loggedUser);
        final TripService tripService = getTripService(testSession);

        TripService tripServiceSpy = Mockito.spy(tripService);
        tripServiceSpy.getTripsByUser(friendUser);
        verify(tripServiceSpy).findTripsByUser(eq(friendUser));
    }

    private TripService getTripService(final UserSession userSession) {
        return new TripService(userSession){
            @Override
            List<Trip> findTripsByUser(User byUser) {
                return null;
            }
        };
    }

    private UserSession getUserSession(User loggedUser) {
        final UserSession testSession = mock(UserSession.class);
        when(testSession.getLoggedUser()).thenReturn(loggedUser);
        return testSession;
    }
}
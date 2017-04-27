package org.craftedsw.harddependencies.trip;

import org.craftedsw.harddependencies.exception.UserNotLoggedInException;
import org.craftedsw.harddependencies.user.User;
import org.craftedsw.harddependencies.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private final UserSession userSession;

    public TripService(UserSession userSession) {
        this.userSession=userSession;
    }

    /**
	 * trouve la liste de voyages d'un user si c'est un ami du user courant
	 */
	public List<Trip> getTripsByUser(User byUser) throws UserNotLoggedInException {
		// recup du "LoggedUser"
		User loggedUser = getSession().getLoggedUser();
		if (loggedUser == null) {
			throw new UserNotLoggedInException();
		}

		// détermine si "isFriend"
		// vrai si un des friends du byUser est le loggedUser
		boolean isFriend = false;
		for (User friend : byUser.getFriends()) {
			if (friend.equals(loggedUser)) {
				isFriend = true;
				break;
			}
		}

		// si isFriend, on appel le DAO pour trouver les trips du byUser
		List<Trip> tripList = new ArrayList<Trip>();
		if (isFriend) {
            tripList = findTripsByUser(byUser);
        }
		return tripList;
	}

    List<Trip> findTripsByUser(User byUser) {
        return TripDAO.findTripsByUser(byUser);
    }

    public UserSession getSession() {
		return userSession;
	}

}

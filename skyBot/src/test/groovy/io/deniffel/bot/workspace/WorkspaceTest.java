package io.deniffel.bot.workspace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkspaceTest {

    private Workspace workspace;

    @Before
    public void setUp() {
        workspace = Workspace.build();
    }

    @Test
    public void enterTwoWorkspaces_theyAreNotTheSame() {
        assertFalse(Workspace.build() == Workspace.build());
    }

    @Test
    public void roomById_returnsRoomEvenIfRoomDoesNotExist() {
        assertNotNull(workspace.enterRoom("roomId"));
    }

    @Test
    public void roomByIdTwice_returnsSameRoom() {
        assertTrue(workspace.enterRoom("roomId") == workspace.enterRoom("roomId"));
    }

    @Test
    public void roomById_roomHasTheGivenId() {
        assertEquals("roomId", workspace.enterRoom("roomId").getId());
    }

    @Test
    public void differentIds_differentRooms() {
        assertFalse(workspace.enterRoom("room1") == workspace.enterRoom("room2"));
    }
}

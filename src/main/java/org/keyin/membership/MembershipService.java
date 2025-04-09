package org.keyin.membership;

import java.util.List;

public class MembershipService {

    private MembershipDAO membershipDAO;

    public MembershipService(MembershipDAO membershipDAO) {
        this.membershipDAO = membershipDAO;
    }

    // Add a new membership to the system (used by trainers/members)
    public void buyMembership(Membership membership) {
        membershipDAO.addMembership(membership);
    }

    // Get all memberships in the system (used by admin)
    public List<Membership> getAllMemberships() {
        return membershipDAO.getAllMemberships();
    }

    // Get memberships for a specific member (used by trainer/member)
    public List<Membership> getMembershipsByMemberId(int memberId) {
        return membershipDAO.getMembershipsByMemberId(memberId);
    }

    // Calculate total revenue from all memberships (used by admin)
    public double getTotalRevenue() {
        return membershipDAO.getTotalRevenue();
    }
}

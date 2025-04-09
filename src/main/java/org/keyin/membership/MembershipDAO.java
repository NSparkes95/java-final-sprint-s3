package org.keyin.membership;

import java.util.List;

public interface MembershipDAO {
    void addMembership(Membership membership);
    List<Membership> getAllMemberships();
    List<Membership> getMembershipsByMemberId(int memberId);
    double getTotalRevenue();
}

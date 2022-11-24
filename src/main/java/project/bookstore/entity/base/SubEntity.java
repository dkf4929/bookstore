package project.bookstore.entity.base;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@MappedSuperclass
public class SubEntity extends BaseEntity {

    @CreatedBy
    @Column(updatable = false)
    private Long createdMemberId;

    @LastModifiedBy
    private Long modifiedMemberId;

    @PrePersist
    public void prePersist() {
        HttpServletRequest request = getRequest();

        if (request != null) {
            if (request.getParameter("memberId") != null) {
                createdMemberId = Long.valueOf(request.getParameter("memberId"));
            } else {
                createdMemberId = 1L;
            }
        }
    }

    @PreUpdate
    public void preUpdate() {
        HttpServletRequest request = getRequest();

        if (request != null) {
            if (request.getParameter("memberId") != null) {
                modifiedMemberId = Long.valueOf(request.getParameter("memberId"));
            } else {
                modifiedMemberId = 1L;
            }
        }
    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }

        return null;
    }
}

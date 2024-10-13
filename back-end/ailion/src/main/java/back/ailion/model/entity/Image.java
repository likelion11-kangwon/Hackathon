package back.ailion.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "uploadFileName", column = @Column(name = "attach_upload_file_name")),
            @AttributeOverride(name = "storeFileName", column = @Column(name = "attach_store_file_name"))
    })
    private FileUpload attachFile;

    @ElementCollection
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "image_id"))
    private List<FileUpload> imageFiles = new ArrayList<>();

    @Builder
    public Image(Post post, FileUpload attachFile, List<FileUpload> imageFiles) {
        this.post = post;
        this.attachFile = attachFile;
        this.imageFiles = imageFiles;
    }
}
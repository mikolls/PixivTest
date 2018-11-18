package com.example.administrator.pixivtest.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/11/16.
 */

public class WeekBean {

    private String status;
    private String count;
    private PaginationBean pagination;
    private List<ResponseBean> response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class PaginationBean {
        /**
         * previous : 1
         * next : 2
         * current : 1
         * per_page : 20
         * total : 500
         * pages : 25
         */

        private String previous;
        private String next;
        private String current;
        private String per_page;
        private String total;
        private String pages;

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages = pages;
        }
    }

    public static class ResponseBean {


        private String content;
        private String mode;
        private String date;
        private List<WorksBean> works;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<WorksBean> getWorks() {
            return works;
        }

        public void setWorks(List<WorksBean> works) {
            this.works = works;
        }

        public static class WorksBean {


            private String rank;
            private String previous_rank;
            private WorkBean work;

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getPrevious_rank() {
                return previous_rank;
            }

            public void setPrevious_rank(String previous_rank) {
                this.previous_rank = previous_rank;
            }

            public WorkBean getWork() {
                return work;
            }

            public void setWork(WorkBean work) {
                this.work = work;
            }

            public static class WorkBean {


                private String id;
                private String title;
                private Object caption;
                private Object tools;
                private ImageUrlsBean image_urls;
                private String width;
                private String height;
                private StatsBean stats;
                private String publicity;
                private String age_limit;
                private String created_time;
                private String reuploaded_time;
                private UserBean user;
                private Object is_manga;
                private Object is_liked;
                private Object favorite_id;
                private String page_count;
                private String book_style;
                private String type;
                private Object metadata;
                private Object content_type;
                private String sanity_level;
                private List<String> tags;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public Object getCaption() {
                    return caption;
                }

                public void setCaption(Object caption) {
                    this.caption = caption;
                }

                public Object getTools() {
                    return tools;
                }

                public void setTools(Object tools) {
                    this.tools = tools;
                }

                public ImageUrlsBean getImage_urls() {
                    return image_urls;
                }

                public void setImage_urls(ImageUrlsBean image_urls) {
                    this.image_urls = image_urls;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public StatsBean getStats() {
                    return stats;
                }

                public void setStats(StatsBean stats) {
                    this.stats = stats;
                }

                public String getPublicity() {
                    return publicity;
                }

                public void setPublicity(String publicity) {
                    this.publicity = publicity;
                }

                public String getAge_limit() {
                    return age_limit;
                }

                public void setAge_limit(String age_limit) {
                    this.age_limit = age_limit;
                }

                public String getCreated_time() {
                    return created_time;
                }

                public void setCreated_time(String created_time) {
                    this.created_time = created_time;
                }

                public String getReuploaded_time() {
                    return reuploaded_time;
                }

                public void setReuploaded_time(String reuploaded_time) {
                    this.reuploaded_time = reuploaded_time;
                }

                public UserBean getUser() {
                    return user;
                }

                public void setUser(UserBean user) {
                    this.user = user;
                }

                public Object getIs_manga() {
                    return is_manga;
                }

                public void setIs_manga(Object is_manga) {
                    this.is_manga = is_manga;
                }

                public Object getIs_liked() {
                    return is_liked;
                }

                public void setIs_liked(Object is_liked) {
                    this.is_liked = is_liked;
                }

                public Object getFavorite_id() {
                    return favorite_id;
                }

                public void setFavorite_id(Object favorite_id) {
                    this.favorite_id = favorite_id;
                }

                public String getPage_count() {
                    return page_count;
                }

                public void setPage_count(String page_count) {
                    this.page_count = page_count;
                }

                public String getBook_style() {
                    return book_style;
                }

                public void setBook_style(String book_style) {
                    this.book_style = book_style;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Object getMetadata() {
                    return metadata;
                }

                public void setMetadata(Object metadata) {
                    this.metadata = metadata;
                }

                public Object getContent_type() {
                    return content_type;
                }

                public void setContent_type(Object content_type) {
                    this.content_type = content_type;
                }

                public String getSanity_level() {
                    return sanity_level;
                }

                public void setSanity_level(String sanity_level) {
                    this.sanity_level = sanity_level;
                }

                public List<String> getTags() {
                    return tags;
                }

                public void setTags(List<String> tags) {
                    this.tags = tags;
                }

                public static class ImageUrlsBean {
                    /**
                     * px_128x128 : https://i.pximg.net/c/128x128/img-master/img/2018/11/10/18/00/10/71588619_p0_square1200.jpg
                     * px_480mw : https://i.pximg.net/c/480x960/img-master/img/2018/11/10/18/00/10/71588619_p0_master1200.jpg
                     * large : https://i.pximg.net/img-original/img/2018/11/10/18/00/10/71588619_p0.jpg
                     */

                    private String px_128x128;
                    private String px_480mw;
                    private String large;

                    public String getPx_128x128() {
                        return px_128x128;
                    }

                    public void setPx_128x128(String px_128x128) {
                        this.px_128x128 = px_128x128;
                    }

                    public String getPx_480mw() {
                        return px_480mw;
                    }

                    public void setPx_480mw(String px_480mw) {
                        this.px_480mw = px_480mw;
                    }

                    public String getLarge() {
                        return large;
                    }

                    public void setLarge(String large) {
                        this.large = large;
                    }
                }

                public static class StatsBean {
                    /**
                     * scored_count : 14382
                     * score : 143820
                     * views_count : 147906
                     * favorited_count : {"public":null,"private":null}
                     * commented_count : null
                     */

                    private String scored_count;
                    private String score;
                    private String views_count;
                    private FavoritedCountBean favorited_count;
                    private Object commented_count;

                    public String getScored_count() {
                        return scored_count;
                    }

                    public void setScored_count(String scored_count) {
                        this.scored_count = scored_count;
                    }

                    public String getScore() {
                        return score;
                    }

                    public void setScore(String score) {
                        this.score = score;
                    }

                    public String getViews_count() {
                        return views_count;
                    }

                    public void setViews_count(String views_count) {
                        this.views_count = views_count;
                    }

                    public FavoritedCountBean getFavorited_count() {
                        return favorited_count;
                    }

                    public void setFavorited_count(FavoritedCountBean favorited_count) {
                        this.favorited_count = favorited_count;
                    }

                    public Object getCommented_count() {
                        return commented_count;
                    }

                    public void setCommented_count(Object commented_count) {
                        this.commented_count = commented_count;
                    }

                    public static class FavoritedCountBean {
                        /**
                         * public : null
                         * private : null
                         */

                        @SerializedName("public")
                        private Object publicX;
                        @SerializedName("private")
                        private Object privateX;

                        public Object getPublicX() {
                            return publicX;
                        }

                        public void setPublicX(Object publicX) {
                            this.publicX = publicX;
                        }

                        public Object getPrivateX() {
                            return privateX;
                        }

                        public void setPrivateX(Object privateX) {
                            this.privateX = privateX;
                        }
                    }
                }

                public static class UserBean {


                    private String id;
                    private String account;
                    private String name;
                    private Object is_following;
                    private Object is_follower;
                    private Object is_friend;
                    private Object is_premium;
                    private ProfileImageUrlsBean profile_image_urls;
                    private Object stats;
                    private Object profile;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getAccount() {
                        return account;
                    }

                    public void setAccount(String account) {
                        this.account = account;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public Object getIs_following() {
                        return is_following;
                    }

                    public void setIs_following(Object is_following) {
                        this.is_following = is_following;
                    }

                    public Object getIs_follower() {
                        return is_follower;
                    }

                    public void setIs_follower(Object is_follower) {
                        this.is_follower = is_follower;
                    }

                    public Object getIs_friend() {
                        return is_friend;
                    }

                    public void setIs_friend(Object is_friend) {
                        this.is_friend = is_friend;
                    }

                    public Object getIs_premium() {
                        return is_premium;
                    }

                    public void setIs_premium(Object is_premium) {
                        this.is_premium = is_premium;
                    }

                    public ProfileImageUrlsBean getProfile_image_urls() {
                        return profile_image_urls;
                    }

                    public void setProfile_image_urls(ProfileImageUrlsBean profile_image_urls) {
                        this.profile_image_urls = profile_image_urls;
                    }

                    public Object getStats() {
                        return stats;
                    }

                    public void setStats(Object stats) {
                        this.stats = stats;
                    }

                    public Object getProfile() {
                        return profile;
                    }

                    public void setProfile(Object profile) {
                        this.profile = profile;
                    }

                    public static class ProfileImageUrlsBean {
                        /**
                         * px_170x170 : https://i.pximg.net/user-profile/img/2016/05/31/14/38/50/11002845_8506eb1a3648f39fdf399e749988688b_170.jpg
                         * px_50x50 : https://i.pximg.net/user-profile/img/2016/05/31/14/38/50/11002845_8506eb1a3648f39fdf399e749988688b_50.jpg
                         */

                        private String px_170x170;
                        private String px_50x50;

                        public String getPx_170x170() {
                            return px_170x170;
                        }

                        public void setPx_170x170(String px_170x170) {
                            this.px_170x170 = px_170x170;
                        }

                        public String getPx_50x50() {
                            return px_50x50;
                        }

                        public void setPx_50x50(String px_50x50) {
                            this.px_50x50 = px_50x50;
                        }
                    }
                }
            }
        }
    }
}

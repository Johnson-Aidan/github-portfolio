#include <chrono>
#include <iostream>
#include <opencv2/opencv.hpp>

std::vector<cv::Point2f> control_points;

void mouse_handler(int event, int x, int y, int flags, void *userdata)
{
    if (event == cv::EVENT_LBUTTONDOWN && control_points.size() < 4)
    {
        std::cout << "Left button of the mouse is clicked - position (" << x << ", "
        << y << ")" << '\n';
        control_points.emplace_back(x, y);
    }
}

void naive_bezier(const std::vector<cv::Point2f> &points, cv::Mat &window)
{
    // TODO: Implement de Casteljau's algorithm

    double t = 0.0;
    double delta = 0.001;
    while (t < 1.0)
    {
        cv::Point2f point = points[0] * std::pow((1 - t), 3)
                          + points[1] * 3 * t * std::pow((1 - t), 2)
                          + points[2] * 3 * std::pow(t, 2) * (1 - t)
                          + points[3] * std::pow(t, 3);
                            
        window.at<cv::Vec3b>(point.y, point.x)[1] = 255;
        t += delta;
    }

}

cv::Point2f recursive_bezier(const std::vector<cv::Point2f> &control_points, float t)
{

    if(control_points.size() == 1)
    {
        return cv::Point2f(control_points[0]);
    }
    else
    {
        std::vector<cv::Point2f> left_control_points(control_points.begin(),control_points.end()-1);
        std::vector<cv::Point2f> right_control_points(control_points.begin()+1,control_points.end());
        cv::Point2f left_bezier = recursive_bezier(left_control_points,t);
        cv::Point2f right_bezier = recursive_bezier(right_control_points,t);

        return cv::Point2f(left_bezier * (1-t) + right_bezier * t);
    }

}

void bezier(const std::vector<cv::Point2f> &control_points, cv::Mat &window)
{
    double t = 0.0;
    double delta = 0.001;
    while(t < 1.0)
    {
        cv::Point2f point = recursive_bezier(control_points,t);
        window.at<cv::Vec3b>(point.y,point.x)[1] = 255;
        t += delta;
    }

}

int main()
{
    cv::Mat window = cv::Mat(700, 700, CV_8UC3, cv::Scalar(0));
    cv::cvtColor(window, window, cv::COLOR_BGR2RGB);
    cv::namedWindow("Bezier Curve", cv::WINDOW_AUTOSIZE);

    cv::setMouseCallback("Bezier Curve", mouse_handler, nullptr);

    int key = -1;
    while (key != 27)
    {
        for (auto &point : control_points)
        {
            cv::circle(window, point, 3, {255, 255, 255}, 3);
        }

        if (control_points.size() == 4)
        {
            naive_bezier(control_points, window);
            bezier(control_points, window);

            cv::imshow("Bezier Curve", window);
            cv::imwrite("my_bezier_curve.png", window);
            key = cv::waitKey(0);

            return 0;
        }

        cv::imshow("Bezier Curve", window);
        key = cv::waitKey(20);
    }

return 0;
}